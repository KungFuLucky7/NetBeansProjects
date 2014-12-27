package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/**
 * FALSEBRANCH <label> - pop the top of the
 * stack; if it's false(0) then branch to <label> else
 * execute the next bytecode
 * 
 * @author Terry Wong
 */
public class FalseBranchCode extends BranchingCode {

    private String label;
    private int addrs;
    private boolean branchStatus = false;

    @Override
    public void init(ArrayList<String> args) {
        label = args.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        if (vm.popRunStack() == 0) {
            vm.setPC(addrs - 1);
        }
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public int ConvertAddrs(int i) {
        addrs = i;
        return addrs;
    }

    public boolean getbranchStatus() {
        return branchStatus;
    }

    public boolean setbranchStatus(boolean v) {
        branchStatus = v;
        return branchStatus;
    }
}
