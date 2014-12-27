package interpreter.bytecode;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVirtualMachine;

/**
 * ReturnCode in Debug mode.
 *
 * @author Terry Wong
 */
public class DebugReturnCode extends ReturnCode {

    @Override
    public void execute(VirtualMachine vm) {
        value = vm.popFrameRunStack();
        vm.restoreRtAddrs();
        DebugVirtualMachine dvm = (DebugVirtualMachine) vm;
        if (dvm.getIsTraceOn()) {
            int i = dvm.decrementTraceCounter();
            while (i > 0) {
                System.out.print(" ");
                i--;
            }
            System.out.print("exit ");
            i = 0;
            while (i < dvm.getFunctionName().length() && dvm.getFunctionName().charAt(i) != '<') {
                System.out.print(dvm.getFunctionName().charAt(i));
                i++;
            }
            System.out.println(": " + value);
        }
        if (dvm.getEnvSize() == dvm.getStepEnvSize()) {
            if (dvm.getIsSetStepOver()) {
                dvm.breakOp();
                dvm.clearIsSetStepOver();
            } else if (dvm.getIsSetStepOut()) {
                dvm.breakOp();
                dvm.clearIsSetStepOut();
            }
            dvm.setStepEnvSize(0);
        }
        if (dvm.getIsSetStepInto()) {
            dvm.breakOp();
            dvm.clearIsSetStepInto();
        }
        dvm.popFrameFunctionEnvironmentRecord();
    }
}