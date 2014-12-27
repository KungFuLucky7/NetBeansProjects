package simpleserver;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerTable extends Object {

    /**
     * GET, HEAD, POST, PUT, and TRACE. The ServerTable class contains the above
     * methods for dynamic binding dispatch. The ServerTable class also contains
     * all the program related information that needs to be kept track of.
     *
     * <p>Copyright: Copyright (c) 2012</p>
     *
     * @author Terry Wong
     */
    private static String HttpdConfKey[] = {"ServerRoot", "ServerAdmin", "Listen", "DocumentRoot", "LogFile", "DirectoryIndex",
        "MaxThreads", "CacheEnabled", "PersistentConnection", "PersistentConnectionTimeOut", "PutAuthentication"};
    private static String MethodKey[] = {"GET", "HEAD", "POST", "PUT", "TRACE"};
    private static String MethodValue[] = {"Get", "Head", "Post", "Put", "Trace"};
    private static String StatusCodeKey[] = {"101", "200", "201", "204", "301", "304",
        "400", "401", "403", "404", "500", "501"};
    private static String StatusCodeValue[] = {"Switching Protocols", "OK", "Created", "No Content", "Moved Permanently",
        "Not Modified", "Bad Request", "Unauthorized", "Forbidden", "Not Found", "Internal Server Error", "Not Implemented"};
    private static String AuthenticationKey[] = {"AuthName", "AuthType", "AuthUserFile", "Require"};
    private static HashMap<String, String> httpdcf = new HashMap<String, String>();
    private static HashMap<String, String> scriptalias = new HashMap<String, String>();
    private static HashMap<String, String> alias = new HashMap<String, String>();
    private static HashMap<String, String> methods = new HashMap<String, String>();
    private static HashMap<String, String> statuscodes = new HashMap<String, String>();
    private static HashMap<String, String> MIMETable = new HashMap<String, String>();
    private static HashMap<String, String> authenticationdir = new HashMap<String, String>();
    private static ArrayList<HashMap<String, String>> securedDirs = new ArrayList<HashMap<String, String>>();
    private static int loginTrials = 0;

    public static void setHttpdConf(String key, String value) {
        httpdcf.put(key, value);
    }

    public static String getHttpdConf(String key) {
        return httpdcf.get(key);
    }

    public static boolean HttpdConfContains(String key) {
        return httpdcf.containsKey(key);
    }

    public static void setScriptAlias(String key, String value) {
        scriptalias.put(key, value);
    }

    public static String getScriptAlias(String key) {
        return scriptalias.get(key);
    }

    public static boolean ScriptAliasContains(String key) {
        return scriptalias.containsKey(key);
    }

    public static void setAlias(String key, String value) {
        alias.put(key, value);
    }

    public static String getAlias(String key) {
        return alias.get(key);
    }

    public static boolean AliasContains(String key) {
        return alias.containsKey(key);
    }

    public static String getMethod(String method) {
        return methods.get(method);
    }

    public static String getStatusCode(String status) {
        return statuscodes.get(status);
    }

    public static void setAuthenticationDirectives(String key, String value) {
        authenticationdir.put(key, value);
    }

    public static String getAuthenticationDirectives(String key) {
        return authenticationdir.get(key);
    }

    public static HashMap<String, String> getAuthenticationDirectivesMap() {
        return authenticationdir;
    }

    public static boolean AuthenticationDirectivesContains(String key) {
        return authenticationdir.containsKey(key);
    }

    public static void setMIMEType(String key, String value) {
        MIMETable.put(key, value);
    }

    public static String getMIMEType(String key) {
        return MIMETable.get(key);
    }

    public static boolean MIMETypeContains(String key) {
        return MIMETable.containsKey(key);
    }

    public static void setSecuredDirs(HashMap<String, String> key) {
        securedDirs.add(key);
    }

    public static HashMap<String, String> getSecuredDirs(String dir) {
        for (int i = 0; i < securedDirs.size(); i++) {
            if (dir.contains(securedDirs.get(i).get("Directory"))) {
                return securedDirs.get(i);
            }
        }
        return null;
    }

    public static void bumpLoginTrials() {
        loginTrials++;
    }

    public static void resetLoginTrials() {
        loginTrials = 0;
    }

    public static int getLoginTrials() {
        return loginTrials;
    }

    public static void init() {
        for (int i = 0; i < HttpdConfKey.length; i++) {
            httpdcf.put(HttpdConfKey[i], "");
        }

        for (int i = 0; i < MethodKey.length; i++) {
            methods.put(MethodKey[i], MethodValue[i]);
        }

        for (int i = 0; i < StatusCodeKey.length; i++) {
            statuscodes.put(StatusCodeKey[i], StatusCodeValue[i]);
        }

        for (int i = 0; i < AuthenticationKey.length; i++) {
            authenticationdir.put(AuthenticationKey[i], "");
        }
    }
}