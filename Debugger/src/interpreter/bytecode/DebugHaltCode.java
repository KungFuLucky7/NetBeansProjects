package interpreter.bytecode;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVirtualMachine;

/**
 * HaltCode in Debug mode.
 * 
 * @author Terry Wong
 */
public class DebugHaltCode extends HaltCode {

    @Override
    public void execute(VirtualMachine vm) {
        DebugVirtualMachine dvm = (DebugVirtualMachine) vm;
        dvm.renewFunctionEnvironmentRecord();
        if (dvm.getIsSetStepInto() || dvm.getIsSetStepOut() || dvm.getIsSetStepOver()) {
            dvm.clearIsSetFlags();
        }
        dvm.setPC(-1);
        vm.exit();
    }
}
