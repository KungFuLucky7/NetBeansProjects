package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;

/**
 * ListBreakpoints: Print the source file and list current breakpoints settings
 * 
 * @author Terry Wong
 */
public class ListBreakpoints extends SetCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
        if (!DebugVirtualMachine.Breakpts.isEmpty()) {
            System.out.print("Breakpoints are set at: ");
            System.out.println(vm.BreakptsToString());
        } else {
            System.out.println("No breakpoints are set.");
        }
    }
}
