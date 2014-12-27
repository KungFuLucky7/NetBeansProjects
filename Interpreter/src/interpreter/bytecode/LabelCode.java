package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/**
 * LABEL <label>; target for branches; (see
 * FALSEBRANCH, GOTO)
 * 
 * @author Terry Wong
 */
public class LabelCode extends BranchingCode {

    private String label;
    private int addrs;

    @Override
    public void init(ArrayList<String> args) {
        label = args.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
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
