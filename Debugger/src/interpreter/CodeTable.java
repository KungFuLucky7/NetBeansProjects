package interpreter;

public class CodeTable extends Object {

    /**
     *  The CodeTable class is used by BCL
     *  Contains a HashMap with keys being the bytecode strings(e.g. "HALT", "POP")
     *  and values being the name of the corresponding class (e.g. "HaltCode", "PopCode")
     */
    private static String CodeKey[] = {"ARGS", "BOP", "CALL", "DUMP", "FALSEBRANCH", "GOTO", "HALT",
        "LABEL", "LIT", "LOAD", "POP", "READ", "RETURN", "STORE", "WRITE"};
    private static String CodeValue[] = {"ArgsCode", "BopCode", "CallCode", "DumpCode", "FalseBranchCode", "GotoCode", "HaltCode",
        "LabelCode", "LitCode", "LoadCode", "PopCode", "ReadCode", "ReturnCode", "StoreCode", "WriteCode"};
    protected static java.util.HashMap<String, String> codes = new java.util.HashMap<String, String>();

    public static String get(String code) {
        return codes.get(code);
    }

    public static void init() {
        for (int i = 0; i < CodeKey.length; i++) {
            codes.put(CodeKey[i], CodeValue[i]);
        }
    }
}