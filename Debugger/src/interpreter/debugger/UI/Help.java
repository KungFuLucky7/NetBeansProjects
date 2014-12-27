package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;

/**
 * ?: Display the command list
 * A help menu.
 * 
 * @author Terry Wong
 */
public class Help extends SetCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
        System.out.println();
        System.out.println("***************Command List***************");
        System.out.println();
        System.out.println(" sor          (StepOver): Step over a line");
        System.out.println();
        System.out.println(" si           (StepInto): Step into a function call on the current line");
        System.out.println();
        System.out.println(" sot          (StepOut): Step out of current activation of a function call");
        System.out.println();
        System.out.println(" sbp n1 n2... (SetBreakpoints): Set breakpoints at valid locations n1 n2...");
        System.out.println("                                in source program");
        System.out.println(" lbp          (ListBreakpoints): List current breakpoints settings");
        System.out.println();
        System.out.println(" cbp n1 n2... (ClearBreakpoints): Clear designated breakpoints n1 n2...");
        System.out.println();
        System.out.println(" dfr          (DisplayFunctionRecord) : Display current function record");
        System.out.println();
        System.out.println(" dlv          (DisplayLocalVars): Display local variables");
        System.out.println();
        System.out.println(" clv vn nvv   (ChangeLocalVars): Change value of local variable");
        System.out.println("                                 vn(variable name) to nvv(new variable value)");
        System.out.println(" dsc          (DisplaySourceCode): Display the source code of");
        System.out.println("                                   the current function");
        System.out.println(" c            (Continue): Continue execution");
        System.out.println();
        System.out.println(" rb           (RollBack): Re-execute the current function");
        System.out.println();
        System.out.println(" t            (Trace): Set whether to print trace information");
        System.out.println("                       on function entry/exit");
        System.out.println(" h            (Halt): Halt execution");
        System.out.println();
    }
}
