package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/**
 * RETURN <funcname>; Return from the current function;
 * <funcname> is used as a comment to indicate the current
 * function
 * RETURN is generated for intrinsic functions
 * 
 * @author Terry Wong
 */
public class ReturnCode extends BranchingCode {

    protected String funcname;
    protected int addrs, value;

    @Override
    public void init(ArrayList<String> args) {
        if (!args.isEmpty()) {
            funcname = args.get(0);
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        value = vm.popFrameRunStack();
        vm.restoreRtAddrs();
    }

    public int getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return funcname;
    }

    @Override
    public int ConvertAddrs(int i) {
        addrs = i;
        return addrs;
    }
}
