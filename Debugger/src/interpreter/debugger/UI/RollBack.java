package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;

/**
 * RollBack: Re-execute the current function
 * 
 * @author Terry Wong
 */
public class RollBack extends RunCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
        vm.reexecuteFunction();
    }
}
