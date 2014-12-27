package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/** 
 * This class accommodates bytecodes that involved address resolving.
 * 
 * @author Terry Wong
 */
public abstract class BranchingCode extends ByteCode {

    @Override
    public abstract void init(ArrayList<String> args);

    @Override
    public abstract void execute(VirtualMachine vm);

    public abstract String getLabel();

    public abstract int ConvertAddrs(int i);
}
