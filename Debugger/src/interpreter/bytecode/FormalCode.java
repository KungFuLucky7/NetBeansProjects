package interpreter.bytecode;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVirtualMachine;
import java.util.ArrayList;

/**
 * Enter the formals into Symbol Table
 * 
 * @author Terry Wong
 */
public class FormalCode extends ByteCode {

    private String Parameter;
    private int offset;

    @Override
    public void init(ArrayList<String> args) {
        Parameter = args.get(0);
        offset = Integer.parseInt(args.get(1));
    }

    @Override
    public void execute(VirtualMachine vm) {
        DebugVirtualMachine dvm = (DebugVirtualMachine) vm;
        dvm.enterSymTab(Parameter, offset);
        if (vm.getNumArgs() != 0) {
            if (DebugVirtualMachine.Breakpts.contains(dvm.getCurrentLine())) {
                dvm.breakOp();
                dvm.clearIsSetFlags();
            } else if (dvm.getIsSetStepInto()) {
                dvm.breakOp();
                dvm.clearIsSetStepInto();
            } else if (dvm.getIsRollBack()) {
                dvm.breakOp();
                dvm.clearIsRollBack();
            }
            vm.setNumArgs(vm.getNumArgs() - 1);
        }
    }
}
