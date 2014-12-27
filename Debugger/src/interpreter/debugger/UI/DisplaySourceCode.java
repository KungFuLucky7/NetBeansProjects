package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;

/**
 * DisplaySourceCode: Display the source code of the current function
 * 
 * @author Terry Wong
 */
public class DisplaySourceCode extends SetCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
        vm.displaySourceCode();
    }
}
