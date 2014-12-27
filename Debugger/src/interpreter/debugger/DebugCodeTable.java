package interpreter.debugger;

import interpreter.CodeTable;

public class DebugCodeTable extends CodeTable {

    /**
     * The DebugCodeTable class is used by DebugBCL with added new bytecodes for
     * debugging Contains a HashMap with keys being the bytecode strings(e.g.
     * "HALT", "POP") and values being the name of the corresponding class (e.g.
     * "HaltCode", "PopCode")
     */
    private static String CodeKey[] = {"ARGS", "BOP", "CALL", "DUMP", "FALSEBRANCH", "FORMAL", "FUNCTION", "GOTO", "HALT",
        "LABEL", "LINE", "LIT", "LOAD", "POP", "READ", "RETURN", "STORE", "WRITE"};
    private static String CodeValue[] = {"DebugArgsCode", "BopCode", "DebugCallCode", "DumpCode", "FalseBranchCode", "FormalCode", "FunctionCode", "GotoCode", "DebugHaltCode",
        "LabelCode", "LineCode", "DebugLitCode", "LoadCode", "DebugPopCode", "ReadCode", "DebugReturnCode", "StoreCode", "WriteCode"};
    private static String CommandKey[] = {"clv", "cbp", "c", "dfr", "dlv", "dsc", "h", "?", "lbp", "rb", "sbp", "si", "sot", "sor", "t"};
    private static String CommandValue[] = {"ChangeLocalVars", "ClearBreakpoints", "Continue", "DisplayFunctionRecord", "DisplayLocalVars", "DisplaySourceCode", "Halt",
        "Help", "ListBreakpoints", "RollBack", "SetBreakpoints", "StepInto", "StepOut", "StepOver", "Trace"};
    private static java.util.HashMap<String, String> commands = new java.util.HashMap<String, String>();

    public static String getCommand(String command) {
        return commands.get(command);
    }

    public static void init() {
        for (int i = 0; i < CodeKey.length; i++) {
            codes.put(CodeKey[i], CodeValue[i]);
        }
        for (int i = 0; i < CommandKey.length; i++) {
            commands.put(CommandKey[i], CommandValue[i]);
        }
    }
}
