package simpleserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

/**
 * <p>Title: HttpdConf.java</p>
 *
 * <p>Description: This class will configure your server to the specifications
 * found within the httpd.conf file. </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * This is an enhanced version. <p>Copyright: Copyright (c) 2012</p>
 *
 * @author Terry Wong
 */
public class HttpdConf {
    /*
     * ServerRoot Provides the path to the root of the server installation.
     * Listen Provides the port number that the server will listen on for
     * incoming requests. DocumentRoot Provides the path to the root of the
     * document tree. LogFile Provides the path AND filename for the logfile.
     * ScriptAlias Provides the path to the root of the cgi-bin directory. Alias
     * Maps a symbolic path in a URL to a real path
     */

    private String line;
    private String ParsedStrings[] = new String[3];
    private static HashMap<String, String> env;

    /**
     * Default constructor to reset your variables and data structures.
     */
    public HttpdConf() {
        env = new HashMap<String, String>();
    }

    /**
     * Reads in a httpd.conf file, parses it and saves the data stored within
     * that file. This allows for proper configuration of your server since the
     * information stored in your configuration file should allow for your
     * server to function.
     *
     * @param path path to your httpd.conf file
     */
    public void readHttpd(String filename) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File("conf" + File.separator + filename)));
            while (in.ready()) {
                line = in.readLine();
                if (line != null && line.trim().length() > 0 && !line.startsWith("#")) {
                    StringTokenizer st = new StringTokenizer(line);
                    ParsedStrings[0] = st.nextToken();
                    if (ServerTable.HttpdConfContains(ParsedStrings[0])) {
                        ParsedStrings[1] = st.nextToken();
                        while (st.hasMoreTokens()) {
                            ParsedStrings[1] += " " + st.nextToken();
                        }
                        ParsedStrings[1] = ParsedStrings[1].replaceAll("\"", "").trim();
                        ServerTable.setHttpdConf(ParsedStrings[0], ParsedStrings[1]);
                        env.put(ParsedStrings[0], ParsedStrings[1]);
                    } else if (ParsedStrings[0].equals("ScriptAlias")) {
                        ParsedStrings[1] = st.nextToken();
                        ParsedStrings[2] = st.nextToken();
                        while (st.hasMoreTokens()) {
                            ParsedStrings[2] += " " + st.nextToken();
                        }
                        ParsedStrings[2] = ParsedStrings[2].replaceAll("\"", "").trim();
                        ServerTable.setScriptAlias(ParsedStrings[1], ParsedStrings[2]);
                    } else if (ParsedStrings[0].equals("Alias")) {
                        ParsedStrings[1] = st.nextToken();
                        ParsedStrings[2] = st.nextToken();
                        while (st.hasMoreTokens()) {
                            ParsedStrings[2] += " " + st.nextToken();
                        }
                        ParsedStrings[2] = ParsedStrings[2].replaceAll("\"", "").trim();
                        ServerTable.setAlias(ParsedStrings[1], ParsedStrings[2]);
                    } else if (ParsedStrings[0].equals("<Directory")) {
                        ParsedStrings[1] = st.nextToken();
                        while (st.hasMoreTokens()) {
                            ParsedStrings[1] += " " + st.nextToken();
                        }
                        ParsedStrings[1] = ParsedStrings[1].replaceAll("\"", "").trim();
                        ParsedStrings[1] = ParsedStrings[1].replace(">", "").trim();
                        ParsedStrings[1] = ParsedStrings[1].replaceAll("/", Matcher.quoteReplacement(File.separator)).trim();
                        System.out.println("Secured Dir: " + ParsedStrings[1]);
                        ServerTable.setAuthenticationDirectives("Directory", ParsedStrings[1]);
                        while (in.ready() && !line.equals("</Directory>")) {
                            line = in.readLine();
                            if (line != null && line.trim().length() > 0) {
                                st = new StringTokenizer(line);
                                ParsedStrings[0] = st.nextToken();
                                if (ServerTable.AuthenticationDirectivesContains(ParsedStrings[0])) {
                                    ParsedStrings[1] = st.nextToken();
                                    while (st.hasMoreTokens()) {
                                        ParsedStrings[1] += " " + st.nextToken();
                                    }
                                    ParsedStrings[1] = ParsedStrings[1].replaceAll("\"", "").trim();
                                    ServerTable.setAuthenticationDirectives(ParsedStrings[0], ParsedStrings[1]);
                                }
                            }
                        }
                        HashMap<String, String> securedDirSpecs = new HashMap<String, String>();
                        securedDirSpecs.putAll(ServerTable.getAuthenticationDirectivesMap());
                        ServerTable.setSecuredDirs(securedDirSpecs);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Error reading the httpd.conf file");
            System.exit(1);
        }
    }

    /**
     * Used to read the mime.types file and save all the information from that
     * file into a data structure that can be used to validate file types when
     * generating response messages.
     *
     * @param path String value of path to mime.types file
     */
    public void readMIME(String filename) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File("conf" + File.separator + filename)));
            if (in == null) {
                in = new BufferedReader(new FileReader(filename.toUpperCase()));
            }
            line = in.readLine();
            //System.out.println(line);
            while (in.ready()) {
                if (line != null && line.trim().length() > 0 && !line.startsWith("#")) {
                    StringTokenizer st = new StringTokenizer(line);
                    ParsedStrings[0] = st.nextToken();
                    while (st.hasMoreTokens()) {
                        ParsedStrings[1] = st.nextToken();
                        ServerTable.setMIMEType(ParsedStrings[1], ParsedStrings[0]);
                    }
                }
                line = in.readLine();
                //System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Error reading the mime.type file");
            System.exit(1);
        }
    }

    public int getListenPort() {
        return Integer.parseInt(ServerTable.getHttpdConf("Listen"));
    }

    public HashMap<String, String> getEnv() {
        return env;
    }

    /**
     * Helper function to see if you've parsed your httpd.conf file properly.
     * Used for debugging purposes.
     */
    public void testPrint() {
        System.out.println("ServerRoot: " + ServerTable.getHttpdConf("ServerRoot"));
        System.out.println("ServerAdmin: " + ServerTable.getHttpdConf("ServerAdmin"));
        System.out.println("DocumentRoot: " + ServerTable.getHttpdConf("DocumentRoot"));
        System.out.println("ListenPort: " + ServerTable.getHttpdConf("Listen"));
        System.out.println("LogFile: " + ServerTable.getHttpdConf("LogFile"));
        System.out.println("ScriptAlias /cgi-bin/: " + ServerTable.getScriptAlias("/cgi-bin/"));
        System.out.println("ScriptAlias /cgi-bin2/: " + ServerTable.getScriptAlias("/cgi-bin2/"));
    }
}
