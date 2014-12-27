package simpleserver;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

/**
 * <p>Title: Request.java</p>
 *
 * <p>Description: Used to store and process requests that come from the client
 * to the server. </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * This is an enhanced version. <p>Copyright: Copyright (c) 2012</p>
 *
 * @author Terry Wong
 */
public class Request {

    private final Socket client;
    private String firstline, line;
    private int index;
    private String methodClass, REQUEST_URI, HTTP_VERSION, queryString = "";
    private byte[] body = new byte[0];
    private boolean scriptRequest = false, aliasRequest = false;
    private String pathInfo = "";
    private static HashMap<String, String> headers;
    private String status = null;
    private Environment env;
    private ArrayList<String> savedHeaders;

    /**
     * Default constructor used to reset your variables and data structures for
     * each new incoming request.
     */
    public Request(Socket client, HashMap<String, String> env) {
        this.client = client;
        this.env = new Environment(env);
        headers = new HashMap<String, String>();
        savedHeaders = new ArrayList<String>();
    }

    public String getFirstLine() {
        return firstline;
    }

    public String getMethodClass() {
        return methodClass;
    }

    public String getREQUEST_URI() {
        return REQUEST_URI;
    }

    public String getHTTP_VERSION() {
        return HTTP_VERSION;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public boolean isScriptRequest() {
        return scriptRequest;
    }

    public boolean isAliasRequest() {
        return aliasRequest;
    }

    public String getQueryString() {
        return queryString;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public String getStatus() {
        return status;
    }

    public Environment getEnv() {
        return env;
    }

    public ArrayList<String> getSavedHeaders() {
        return savedHeaders;
    }

    /**
     * Print function used for debugging purposes. Helpful to make sure you are
     * parsing the incoming request properly.
     */
    public void print() {
        System.out.println("The firstline is: " + firstline);
        System.out.println("The method was " + methodClass);
        env.setEnv("Method", methodClass);
        System.out.println("The Request URL was " + REQUEST_URI);
        env.setEnv("REQUEST_URI", REQUEST_URI);
        System.out.println("The HTTP version is " + HTTP_VERSION);
        env.setEnv("HTTP_VERSION", HTTP_VERSION);
        System.out.println("The path is " + pathInfo);
        if (queryString != null) {
            System.out.println("The query string was " + queryString);
            env.setEnv("queryString", queryString);
        }
        String contentLength = (String) headers.get("Content-Length");
        if (contentLength != null) {
            System.out.println("The message body was: \n" + body);
        }
    }

    /**
     * public function used by request object to parse the information passed
     * through from the client to the server and save it for future use.
     *
     * @param first String representation of the first line of the request from
     * the client. Passed in as one long string which can easily be parsed.
     */
    public void parse() throws Exception {
        env.setEnv("remoteAddress", client.getRemoteSocketAddress().toString());
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        firstline = in.readLine();
        if (in.ready() && firstline != null && firstline.trim().length() > 0) {
            StringTokenizer st = new StringTokenizer(firstline);
            String method = st.nextToken().toUpperCase();
            if (method != null && checkRequest(method)) {
                methodClass = ServerTable.getMethod(method);
            }
            setVarFirstLine();
            readRequest(in);
        }
    }

    /**
     * private function used by the request object to determine whether an
     * incoming request is a valid request or not. Useful when throwing error
     * messages.
     *
     * @return true if request is valid, false otherwise
     */
    private boolean checkRequest(String request) {
        if (ServerTable.getMethod(request) == null) {
            status = "501";
            return false;
        } else {
            return true;
        }
    }

    /**
     * private function used by the request object to grab variables that may
     * have been passed to the server when the request was made.
     */
    private void setVarFirstLine() throws IOException {
        index = firstline.indexOf(" ");
        line = firstline.substring(index + 1);
        index = line.lastIndexOf(" ");
        REQUEST_URI = line.substring(0, index);
        if (REQUEST_URI != null && REQUEST_URI.trim().length() > 0) {
            index = REQUEST_URI.trim().indexOf("?");
            if (index > 0) {
                queryString = REQUEST_URI.substring(index + 1);
                REQUEST_URI = REQUEST_URI.substring(0, index);
            }
            index = REQUEST_URI.lastIndexOf("/");
            String suffix = REQUEST_URI.substring(index);
            int filenameindex = suffix.indexOf(".");
            if (filenameindex == -1) {
                env.setEnv("pathInfo", suffix);
                pathInfo = ServerTable.getHttpdConf("DocumentRoot") + suffix;
                pathInfo = pathInfo.replaceAll("/", Matcher.quoteReplacement(File.separator)).trim();
                env.setEnv("pathTranslated", pathInfo);
                REQUEST_URI = REQUEST_URI.substring(0, index);
            }
            index = REQUEST_URI.trim().indexOf("/", 1);
            if (index > 0) {
                String aliasstr1 = REQUEST_URI.substring(0, index);
                String aliasstr2 = REQUEST_URI.substring(0, index + 1);
                int endindex = REQUEST_URI.trim().lastIndexOf("/");
                if (ServerTable.ScriptAliasContains(aliasstr1)) {
                    scriptRequest = true;
                    pathInfo = ServerTable.getScriptAlias(aliasstr1) + REQUEST_URI.substring(index, endindex + 1);
                } else if (ServerTable.ScriptAliasContains(aliasstr2)) {
                    scriptRequest = true;
                    pathInfo = ServerTable.getScriptAlias(aliasstr2) + REQUEST_URI.substring(index + 1, endindex + 1);
                } else if (ServerTable.AliasContains(aliasstr1)) {
                    aliasRequest = true;
                    pathInfo = ServerTable.getAlias(aliasstr1) + REQUEST_URI.substring(index, endindex + 1);
                } else if (ServerTable.AliasContains(aliasstr2)) {
                    aliasRequest = true;
                    pathInfo = ServerTable.getAlias(aliasstr2) + REQUEST_URI.substring(index + 1, endindex + 1);
                }
            }
            if (!scriptRequest && !aliasRequest) {
                pathInfo = ServerTable.getHttpdConf("DocumentRoot") + REQUEST_URI;
                index = pathInfo.lastIndexOf("/");
                pathInfo = pathInfo.substring(0, index);
                System.out.println("Path: " + pathInfo);
            }
        } else {
            status = "400";
        }
        index = firstline.lastIndexOf(" ");
        HTTP_VERSION = firstline.substring(index + 1);
        print();
    }

    /**
     * private function used by the request object to parse the rest of the
     * request message (e.g. other headers and the body of the message) from the
     * client so it can be used later when actual processing of the request
     * happens.
     *
     * @param inFile BufferedReader object that comes through the socket. Needs
     * to be processed properly before the data stored within it can be used.
     */
    private void readRequest(BufferedReader in) throws IOException {
        headers.put("Host", "");
        headers.put("Content-Type", "");
        headers.put("Referer", "");
        headers.put("User-Agent", "");
        headers.put("Cookie", "");
        // read headers
        line = in.readLine();
        while (line != null && line.trim().length() > 0) {
            index = line.indexOf(": ");
            if (index > 0) {
                headers.put(line.substring(0, index), line.substring(index + 2));
                System.out.println(line);
                savedHeaders.add(line);
            } else {
                break;
            }
            line = in.readLine();
        }
        // read body
        String contentLength = (String) headers.get("Content-Length");
        if (contentLength != null) {
            env.setEnv("Content-Length", contentLength);
            int length = Integer.parseInt(contentLength.trim());
            body = new byte[length];
            int count = 0, byteRead;
            while (count < length) {
                byteRead = in.read();
                body[count++] = (byte) byteRead;
            }
        } else if (methodClass.equals("Put")) {
            ByteArrayOutputStream tmp = new ByteArrayOutputStream();
            int byteRead;
            while (in.ready()) {
                byteRead = in.read();
                tmp.write(byteRead);
            }
            body = tmp.toByteArray();
        }
    }
}
