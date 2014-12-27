package simpleserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the web server's main class for execution. It also contains a thread
 * class that applies support of the multi-threading functionality for the main
 * program.
 *
 * This is an enhanced version. <p>Copyright: Copyright (c) 2012</p>
 *
 * @author Terry Wong
 */
public class SimpleServer {
    //class variable to keep track of number of threads created

    public static int numThreadsCreated = 0;

    public SimpleServer() {
        ServerTable.init();
    }

    void runServer() {
        // TODO Auto-generated method stub
        ServerSocket server = null;
        Socket client = null;

        try {
            // Read in "conf/httpd.conf" and make an object that handles all information about it 
            // HttpdConf, Request are classes you have to implement. 
            // ServerSocket, Socket are availalbe Java classes. 
            HttpdConf hcf = new HttpdConf();
            hcf.readHttpd("httpd.conf");
            hcf.readMIME("mime.types");
            hcf.testPrint();
            server = new ServerSocket(hcf.getListenPort());
            System.out.println("Opened socket " + hcf.getListenPort());
            while (true) {
                // keeps listening for new clients, one at a time 
                try {
                    client = server.accept(); // waits for client here 
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    System.err.println("Error opening socket");
                    System.exit(1);
                }
                if (numThreadsCreated <= Integer.parseInt(ServerTable.getHttpdConf("MaxThreads"))) {
                    new clientThread(client, hcf).start();
                } else {
                    System.err.println("The maximum number of threads has been reached.");
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Error starting server");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new SimpleServer().runServer();
    }
}

/**
 * Private class used for multithreading
 */
class clientThread extends Thread {

    private Socket client = null;
    private HttpdConf hcf = null;
    private boolean KeepAlive = false;
    private int TimeOut;

    /**
     * Constructor used to start a thread.
     *
     * @param incoming Socket value which designates the socket used by the
     * client
     * @param hcf HttpdConf object created upon server startup.
     */
    public clientThread(Socket client, HttpdConf hcf) {
        this.client = client;
        this.hcf = hcf;
        incrementNumThreadsCreated();
        System.out.println("ENTER_numThreadsCreated: " + SimpleServer.numThreadsCreated);
        KeepAlive = ServerTable.getHttpdConf("PersistentConnection").equalsIgnoreCase("ON");
        TimeOut = Integer.parseInt(ServerTable.getHttpdConf("PersistentConnectionTimeOut"));
    }

    /**
     * Used to run your server thread. Here is where you will be processing all
     * requests and returning your responses.
     */
    public void run() {
        // Connection is built, so read stream from the socket and parse request
        try {
            if (!KeepAlive) {
                Request request = new Request(client, hcf.getEnv());
                request.parse(); // process request
                Response response = new Response(client);
                response.processRequest(request);
                response.writeOutput(); // Generate a response
                client.close();
            } else {
                client.setKeepAlive(KeepAlive);
                System.out.println("Persistent Connection is ON with a timeout of " + TimeOut + " seconds");
                long startTime = System.nanoTime();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while (in.ready() || ((System.nanoTime() - startTime) / 1000000000) <= TimeOut) {
                    if (in.ready()) {
                        Request request = new Request(client, hcf.getEnv());
                        request.parse(); // process request
                        Response response = new Response(client);
                        response.processRequest(request);
                        response.writeOutput(); // Generate a response
                    }
                }
                in.close();
                client.close();
                System.out.println("Persistent Connection exited");
            }
            decrementNumThreadsCreated();
            System.out.println("EXIT_numThreadsCreated: " + SimpleServer.numThreadsCreated);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("Error processing request");
        }
    }

    public synchronized void incrementNumThreadsCreated() {
        SimpleServer.numThreadsCreated++;
    }

    public synchronized void decrementNumThreadsCreated() {
        SimpleServer.numThreadsCreated--;
    }
}
