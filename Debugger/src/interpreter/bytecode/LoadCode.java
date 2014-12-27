package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/**
 * LOAD n <id>; push the value in the slot which is offset n from the
 * start of the frame; <id> is used as a comment, it's the variable
 * name where the data is stored
 * 
 * @author Terry Wong
 */
public class LoadCode extends ByteCode {

    private int n;
    private String id;

    @Override
    public void init(ArrayList<String> args) {
        if (args.size() == 1) {
            n = Integer.parseInt(args.get(0));
        } else {
            n = Integer.parseInt(args.get(0));
            id = args.get(1);
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.loadRunStack(n);
    }

    public int getValue() {
        return n;
    }

    public String getID() {
        return id;
    }
}
