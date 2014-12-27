package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;

/**
 * DisplayLocalVars: Display local variables
 * 
 * @author Terry Wong
 */
public class DisplayLocalVars extends SetCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
        vm.displayLocalVars();
    }
}
