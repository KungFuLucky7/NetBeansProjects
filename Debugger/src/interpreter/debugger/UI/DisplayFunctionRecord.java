package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;

/**
 * DisplayFunctionRecord : Display current function record
 * 
 * @author Terry Wong
 */
public class DisplayFunctionRecord extends SetCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
        vm.displayFunctionRecord();
    }
}
