package interpreter.bytecode;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugByteCodeLoader;
import interpreter.debugger.DebugVirtualMachine;
import java.util.ArrayList;

/**
 * Enter the current source line number.
 * 
 * @author Terry Wong
 */
public class LineCode extends ByteCode {

    private int currentLine;

    @Override
    public void init(ArrayList<String> args) {
        currentLine = Integer.parseInt(args.get(0));
        if (currentLine != -1) {
            DebugByteCodeLoader.savedSourceFile.get(currentLine - 1).setBreakptSetAvailStatus();
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        DebugVirtualMachine dvm = (DebugVirtualMachine) vm;
        dvm.setCurrentLine(currentLine);
        if (dvm.isEnvSizeTheSame()) {
            if (DebugVirtualMachine.Breakpts.contains(currentLine) && !dvm.getIsRollBack()) {
                dvm.breakOp();
                dvm.clearIsSetFlags();
            } else if (dvm.getIsSetStepInto()) {
                dvm.breakOp();
                dvm.clearIsSetStepInto();
            }
            if (dvm.getEnvSize() == dvm.getStepEnvSize()) {
                if (dvm.getIsSetStepOver()) {
                    dvm.breakOp();
                    dvm.clearIsSetStepOver();
                }
            }
        }
    }

    public int getCurrentLine() {
        return currentLine;
    }
}
