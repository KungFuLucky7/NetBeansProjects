package interpreter.bytecode;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVirtualMachine;
import java.util.ArrayList;

/**
 * Enter function information into current FunctionEnvironmentRecord.
 * 
 * @author Terry Wong
 */
public class FunctionCode extends ByteCode {

    private String funcname;
    private int startLine, endLine;

    @Override
    public void init(ArrayList<String> args) {
        funcname = args.get(0);
        startLine = Integer.parseInt(args.get(1));
        endLine = Integer.parseInt(args.get(2));
    }

    @Override
    public void execute(VirtualMachine vm) {
        DebugVirtualMachine dvm = (DebugVirtualMachine) vm;
        dvm.setFunction(funcname, startLine, endLine);
        dvm.setCurrentLine(startLine);
        if (vm.getNumArgs() == 0) {
            if (DebugVirtualMachine.Breakpts.contains(dvm.getCurrentLine()) && dvm.getCurrentLine() != 1) {
                dvm.breakOp();
                dvm.clearIsSetFlags();
            } else if (dvm.getIsSetStepInto()) {
                dvm.breakOp();
                dvm.clearIsSetStepInto();
            } else if (dvm.getIsRollBack()) {
                dvm.breakOp();
                dvm.clearIsRollBack();
            }
        }
        dvm.setEnvSize();
    }
}
