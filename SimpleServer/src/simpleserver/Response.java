package simpleserver;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import simpleserver.method.Method;

/**
 * <p>Title: Response.java</p>
 *
 * <p>Description: Used to process incoming requests to the server and generate
 * the appropriate response to that request. This is where most of the server
 * processing is done, thus making it a very important class to the
 * implementation of your server.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * This is an enhanced version. <p>Copyright: Copyright (c) 2012</p>
 *
 * @author Terry Wong
 */
public class Response {

    private final Socket client;
    private Request request;
    private String line, directory = "", filename = "", extension = "", filetype = "";
    private static String status = null;
    private File requestedfile;
    private long ContentLength;
    private Environment env;
    private Authentication authen;
    private String username = "";
    private DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
    private ArrayList<String> savedHeaders;
    private boolean serverError = false;

    /**
     * Default constructor for the response object. Variables are reset and/or
     * intialized here. These variables will be used throughout request
     * processing and response generation.
     */
    public Response(Socket client) {
        this.client = client;
    }

    /**
     * Used to process the request that came from the client to the server.
     * There are many things that need to be checked and verified during the
     * processing of a request. You will need to check for authentication,
     * errors, cgi scripts, type of request, etc, and handle each condition
     * appropriately.
     *
     * HINT: it helps to use boolean flags throughout your code to check for the
     * various conditions that may or may not occur.
     *
     * @param myRequest Request object that was generated and stores all the
     * request information that came in from the client
     * @param MIMETable Hashtable which contains the various mime types the
     * server can handle.
     * @param env Environment object that contains the environment variables
     * necessary for cgi script execution.
     */
    public void processRequest(Request request) {
        this.request = request;
        this.env = request.getEnv();
        this.savedHeaders = request.getSavedHeaders();
        status = request.getStatus();
        if (status != null && status.equals("501")) {
            serverError = true;
        }
        if (status == null) {
            directory = request.getPathInfo();
            directory = directory.replaceAll("/", Matcher.quoteReplacement(File.separator)).trim();
            System.out.println("directory: " + directory);
            HashMap<String, String> securedDirSpecs = ServerTable.getSecuredDirs(directory);
            if (securedDirSpecs != null && request.getHeaders().containsKey("Authorization")) {
                ServerTable.bumpLoginTrials();
                authen = new Authentication(this);
                try {
                    username = authen.checkAuth(request.getHeaders().get("Authorization"));
                } catch (InternalServerErrorException ex) {
                    status = "500";
                    serverError = true;
                }
                if (!serverError) {
                    if (username == null) {
                        if (ServerTable.getLoginTrials() < 3) {
                            status = "401";
                        } else if (ServerTable.getLoginTrials() >= 3) {
                            status = "403";
                            ServerTable.resetLoginTrials();
                        }
                        username = "";
                    } else {
                        line = securedDirSpecs.get("Require");
                        if (line != null && line.trim().length() > 0) {
                            StringTokenizer st1 = new StringTokenizer(line);
                            if (st1.hasMoreTokens()) {
                                line = st1.nextToken();
                                if (line.equals("user") && st1.hasMoreTokens()) {
                                    line = st1.nextToken();
                                    StringTokenizer st2 = new StringTokenizer(line, ",");
                                    boolean userFound = false;
                                    while (st2.hasMoreTokens()) {
                                        if (username.equals(st2.nextToken())) {
                                            userFound = true;
                                            System.out.println("Found: " + username);
                                            break;
                                        }
                                    }
                                    if (userFound == false) {
                                        if (ServerTable.getLoginTrials() < 3) {
                                            status = "401";
                                        } else if (ServerTable.getLoginTrials() >= 3) {
                                            status = "403";
                                            ServerTable.resetLoginTrials();
                                        }
                                        username = "";
                                    }
                                } else if (!line.equals("valid-user")) {
                                    status = "403";
                                }
                            }
                        }
                    }
                }
            } else if (securedDirSpecs != null) {
                status = "401";
            }
            if (status == null) {
                env.setEnv("user", username);
                line = request.getREQUEST_URI();
                if (line != null && line.trim().length() > 0) {
                    int index = line.lastIndexOf("/");
                    if (index > 0 && index != line.length() - 1) {
                        filename = line.substring(index + 1);
                        System.out.println("filename: " + filename);
                        env.setEnv("filename", filename);
                        index = filename.indexOf(".");
                        if (index >= 0) {
                            extension = filename.substring(index + 1);
                            System.out.println("extension: " + extension);
                            if (ServerTable.MIMETypeContains(extension)) {
                                index = ServerTable.getMIMEType(extension).indexOf("/");
                                filetype = ServerTable.getMIMEType(extension).substring(0, index);
                                System.out.println("filetype: " + filetype);
                            } else if (!request.isScriptRequest()) {
                                status = "400";
                            }
                        }
                    } else if (index == 0) {
                        filename = line.substring(index + 1);
                        System.out.println("filename: " + filename);
                        env.setEnv("filename", filename);
                    } else {
                        status = "400";
                    }
                    if (filename.equals("")) {
                        status = "400";
                    }
                    boolean isSpace;
                    do {
                        isSpace = false;
                        try {
                            requestedfile = new File(directory + File.separator + filename);
                            if (requestedfile.isDirectory()) {
                                status = "400";
                            }
                            FileReader filereader = new FileReader(requestedfile);
                        } catch (FileNotFoundException e) {
                            if (filename.contains("%20")) {
                                filename = filename.replaceAll("%20", " ").trim();
                                isSpace = true;
                            } else if (!request.getMethodClass().equals("Put") && status != "400") {
                                status = "404";
                            }
                        }
                    } while (isSpace == true);
                    if (request.getMethodClass().equals("Post") && !request.isScriptRequest()) {
                        status = "400";
                    }
                    if (request.getMethodClass().equals("Put") && ServerTable.getHttpdConf("PutAuthentication").equalsIgnoreCase("ON") && username.equals("")) {
                        status = "401";
                    }
                    if (status == null) {
                        if (request.getMethodClass().equals("Post") || (request.getMethodClass().equals("Put") && !requestedfile.exists())) {
                            status = "201";
                        } else if (request.getMethodClass().equals("Put") && requestedfile.exists()) {
                            status = "204";
                        } else if (!request.isScriptRequest() && ServerTable.getHttpdConf("CacheEnabled").equalsIgnoreCase("ON")) {
                            String lm = dateFormat.format(requestedfile.lastModified());
                            String ims = request.getHeaders().get("If-Modified-Since");
                            if (ims != null && ims.equals(lm)) {
                                status = "304";
                            } else {
                                status = "200";
                            }
                        } else {
                            status = "200";
                        }
                    }
                } else {
                    status = "400";
                }
            }
        }
    }

    /**
     * Used to output a correctly formatted response to the client. This
     * function will need to process any output from a cgi script as well as
     * generate the appropriate headers and body required by an HTTP response.
     *
     * @param output OutputStream object that will be used to send the response
     * back to the socket.
     */
    public void writeOutput() throws Exception {
        BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
        if (!serverError) {
            int status = Integer.valueOf(this.status);
            switch (status) {
                case 400:
                    line = ServerTable.getHttpdConf("DirectoryIndex");
                    StringTokenizer st = new StringTokenizer(line);
                    boolean defaultFileExists = false;
                    while (!defaultFileExists) {
                        if (st.hasMoreTokens()) {
                            String defaultfilename = st.nextToken();
                            requestedfile = new File(ServerTable.getHttpdConf("DocumentRoot") + File.separator + defaultfilename);
                            if (requestedfile.exists()) {
                                defaultFileExists = true;
                            }
                        } else {
                            throw new IOException();
                        }
                    }
                    extension = "html";
                    break;
                case 401:
                    requestedfile = new File(ServerTable.getHttpdConf("ServerRoot") + File.separator + "error" + File.separator + "401.html");
                    extension = "html";
                    break;
                case 403:
                    requestedfile = new File(ServerTable.getHttpdConf("ServerRoot") + File.separator + "error" + File.separator + "403.html");
                    extension = "html";
                    break;
                case 404:
                    requestedfile = new File(ServerTable.getHttpdConf("ServerRoot") + File.separator + "error" + File.separator + "404.html");
                    extension = "html";
                    break;
                default:
                    requestedfile = new File(directory + File.separator + filename);
                    break;
            }
            ContentLength = requestedfile.length();
        }
        writeToLog(request);
        PrintWriter writer = new PrintWriter(out, true);  // output to the client
        // every response will always have the status-line, date, and server name
        writer.println(request.getHTTP_VERSION() + " " + status + " " + ServerTable.getStatusCode(status));
        Date date = new Date();
        writer.println("Date: " + dateFormat.format(date));
        writer.println("Server: CSC 667");
        writer.println("ServerAdmin: " + ServerTable.getHttpdConf("ServerAdmin"));
        writer.println("User-Agent: " + request.getHeaders().get("User-Agent"));
        if (ServerTable.getHttpdConf("PersistentConnection").equalsIgnoreCase("ON")) {
            writer.println("Connection: keep-alive");
        } else {
            writer.println("Connection: close");
        }
        if (status.equals("401")) {
            writer.println("WWW-Authenticate: " + ServerTable.getSecuredDirs(directory).get("AuthType") + " realm=\"" + ServerTable.getSecuredDirs(directory).get("AuthName") + "\"");
        }
        if (!serverError) {
            if (ServerTable.getHttpdConf("CacheEnabled").equalsIgnoreCase("ON")) {
                writer.println("Last-Modified: " + dateFormat.format(requestedfile.lastModified()));
                writer.println("Expires: Sat, 23 Nov 2013 00:00:00 GMT");
            }
            Method prompt = (Method) (Class.forName("simpleserver.method." + request.getMethodClass())).newInstance();
            prompt.execute(this, writer);
        }
    }

    /**
     * write the body of the response
     */
    public void writeBody(PrintWriter writer) throws Exception {
        BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
        if (status.equals("304")) {
            writer.println("");
            System.out.println("Cached: Finished generating response");
        } else {
            if (request.isScriptRequest()) {
                processCGI(request, out);
            } else {
                writer.println("Content-Length: " + ContentLength);
                writer.println("Content-Type: " + ServerTable.getMIMEType(extension));
                writer.println("");
                if (filetype.equals("text")) {
                    BufferedReader in = new BufferedReader(new FileReader(requestedfile));
                    while (in.ready()) {
                        writer.println(in.readLine());
                    }
                } else {
                    byte[] bytearray = new byte[(int) ContentLength];
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(requestedfile));
                    in.read(bytearray, 0, bytearray.length);
                    out.write(bytearray, 0, bytearray.length);
                    out.flush();
                }
            }
            System.out.println("Finished generating response");
        }
    }

    public void setStatus(String code) {
        status = code;
    }

    public String getStatus() {
        return status;
    }

    public String getDirectory() {
        return directory;
    }

    public ArrayList<String> getSavedHeaders() {
        return savedHeaders;
    }

    /**
     * public function used when processing a Put request from the client. Here,
     * you will handle a put request if it is requested. You will need to use
     * the body of the request to modify the existing file.
     */
    public synchronized void processPutRequest() throws IOException {
        if (!requestedfile.exists()) {
            requestedfile.createNewFile();
        }
        if (requestedfile.isFile()) {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(requestedfile));
            byte[] bytearray = request.getBody();
            out.write(bytearray, 0, bytearray.length);
            out.flush();
            System.out.println("Finished the put request");
        } else {
            System.err.println("Error writing to file");
        }
    }

    /**
     * Private function specifically used to handle output from a cgi script.
     * You will need to check the header passed back from the cgi script to
     * determine the status code of the response. From there, add your headers,
     * attach the body and add any other server directives that need to be
     * included.
     *
     * @param dataOut BufferedOutputStream object that will write to the client
     */
    private void processCGI(Request request, BufferedOutputStream out) throws Exception {
        ProcessBuilder pb;
        File scriptfile = new File(request.getPathInfo() + File.separator + filename);
        BufferedReader br = new BufferedReader(new FileReader(scriptfile));
        String ExePath = br.readLine().replace("#!", "");
        ExePath = ExePath.replace("\"", "");
        System.out.println("ExePath: " + ExePath);
        System.out.println("filename: " + filename);
        pb = new ProcessBuilder(ExePath, filename, request.getQueryString());
        Map<String, String> scriptenv = pb.environment();
        //set environment variables from request headers
        HashMap<String, String> headers = request.getHeaders();
        scriptenv.put("SERVER_NAME", env.getEnv("Server"));
        scriptenv.put("SERVER_SOFTWARE", env.getEnv("Server") + "/1.0");
        scriptenv.put("SERVER_ADMIN", env.getEnv("ServerAdmin"));
        scriptenv.put("SERVER_PORT", env.getEnv("Listen"));
        scriptenv.put("SERVER_PROTOCOL", env.getEnv("HTTP_VERSION"));
        scriptenv.put("GATEWAY_INTERFACE", env.getEnv("Gateway Interface"));
        scriptenv.put("REQUEST_METHOD", env.getEnv("Method"));
        scriptenv.put("PATH_INFO", env.getEnv("pathInfo"));
        scriptenv.put("PATH_TRANSLATED", env.getEnv("pathTranslated"));
        scriptenv.put("SCRIPT_NAME", env.getEnv("filename"));
        scriptenv.put("QUERY_STRING", env.getEnv("queryString"));
        scriptenv.put("HTTP_ACCEPT", headers.get("Accept"));
        scriptenv.put("REMOTE_HOST", headers.get("Host"));
        scriptenv.put("REMOTE_ADDR", env.getEnv("remoteAddress"));
        scriptenv.put("REMOTE_USER", env.getEnv("user"));
        scriptenv.put("CONTENT_TYPE", headers.get("Content-Type"));
        scriptenv.put("CONTENT_LENGTH", env.getEnv("Content-Length"));
        scriptenv.put("HTTP_REFERER", headers.get("Referer"));
        scriptenv.put("HTTP_USER_AGENT", request.getHeaders().get("User-Agent"));
        scriptenv.put("HTTP_COOKIE", headers.get("Cookie"));
        System.out.println("Directory: " + request.getPathInfo());
        pb.directory(new File(request.getPathInfo()));
        Process p = pb.start();
        if (request.getMethodClass().equals("Post") && !headers.get("Content-Length").equals("")) {
            BufferedOutputStream stdout = new BufferedOutputStream(p.getOutputStream());
            byte[] bytearray = request.getBody();
            stdout.write(bytearray, 0, bytearray.length);
            stdout.flush();
        }
        BufferedInputStream in = new BufferedInputStream(p.getInputStream());
        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            tmp.write(buf, 0, len);
        }
        byte[] bytearray = tmp.toByteArray();
        ContentLength = bytearray.length;
        System.out.println("Content-Length: " + ContentLength);
        PrintWriter writer = new PrintWriter(out, true);
        writer.println("Content-Length: " + ContentLength);
        out.write(bytearray, 0, bytearray.length);
        out.flush();
    }

    /**
     * Used to write the appropriate information to the log file.
     *
     * @param logPath String value which contains the location of your log file
     * @param host String value that contains the address of the client who made
     * the request
     */
    private synchronized void writeToLog(Request request) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(ServerTable.getHttpdConf("LogFile")), true));
            if (request.getHeaders().get("Host") != null) {
                writer.write(request.getHeaders().get("Host") + " ");
            } else {
                writer.write("localhost ");
            }
            writer.write("remotelogname ");
            if (env.getEnv("user").equals("")) {
                writer.write("general_user ");
            } else {
                writer.write(username + " ");
            }
            Date date = new Date();
            writer.write("[" + dateFormat.format(date) + "] ");
            writer.write("\"" + request.getFirstLine() + "\" ");
            writer.write(status + " ");
            writer.write(ContentLength + " bytes");
            writer.newLine();
            writer.close();
        } catch (IOException e) {  // if there is an error in reading the file
            System.err.println(e.getMessage());
        }
    }
}
