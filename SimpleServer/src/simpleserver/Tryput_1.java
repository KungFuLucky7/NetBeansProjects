package simpleserver;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author acer
 */
public class Tryput_1 {

    public Tryput_1() {
    }

    public static void main(String[] args) {
        String s;
        Boolean which = true;
        if (which) {
            try {
                Socket server = new Socket("localhost", 8199);
                BufferedReader fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
                PrintWriter toServer = new PrintWriter(server.getOutputStream(), true);

                toServer.println("PUT /webserver.txt HTTP/1.1");
                toServer.println();
                toServer.println("This is the contents of webserver.txt. You may make your program to receive the file name as an argument or through stdI/O, and then read in the file");


                while ((s = fromServer.readLine()) != null) {
                    System.out.println(s);
                }
                fromServer.close();
                toServer.close();
                server.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Socket server = new Socket("localhost", 8199);
                BufferedReader fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
                PrintWriter toServer = new PrintWriter(server.getOutputStream(), true);

                toServer.println("GET /images/academic.jpg HTTP/1.1");
                //toServer.println("If-Modified-Since: Fri, 15 Feb 2008 22:56:36 GMT");
                toServer.println();

                while ((s = fromServer.readLine()) != null) {
                    System.out.println(s);
                }
                fromServer.close();
                toServer.close();
                server.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
