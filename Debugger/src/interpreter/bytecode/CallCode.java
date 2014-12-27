package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/**
 * CALL <funcname> - transfer control to the indicated
 * function
 * 
 * @author Terry Wong
 */
public class CallCode extends BranchingCode {

    protected String funcname, args = null;
    protected int addrs, argsNum;

    @Override
    public void init(ArrayList<String> args) {
        funcname = args.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        argsNum = vm.getNumArgs();
        args = vm.peekArgsRunStack(argsNum);
        vm.saveRtAddrs();
        vm.setPC(addrs - 1);
    }

    public String getArgs() {
        return args;
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
