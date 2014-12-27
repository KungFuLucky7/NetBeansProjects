package interpreter.bytecode;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVirtualMachine;

/**
 * ArgsCode in Debug mode.
 * 
 * @author Terry Wong
 */
public class DebugArgsCode extends ArgsCode {

    @Override
    public void execute(VirtualMachine vm) {
        DebugVirtualMachine dvm = (DebugVirtualMachine) vm;
        dvm.newFrameAtFunctionEnvironmentRecord();
        dvm.setParameterListFER(numArgs);
        if (numArgs != 0) {
            vm.newFrameAtRunStack(vm.sizeofRunStack() - numArgs);
        } else {
            vm.newFrameAtRunStack(vm.sizeofRunStack());
        }
        dvm.setNumArgs(numArgs);
    }
}