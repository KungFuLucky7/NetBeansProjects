package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/**
 * GOTO <label>
 * 
 * @author Terry Wong
 */
public class GotoCode extends BranchingCode {

    private String label;
    private int addrs;

    @Override
    public void init(ArrayList<String> args) {
        label = args.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.setPC(addrs - 1);
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
}
