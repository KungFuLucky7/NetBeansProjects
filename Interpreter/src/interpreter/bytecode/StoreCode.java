package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/**
 * STORE n <id> - pop the top of the stack; store value into
 * the offset n from the start of the frame; <id> is used as a
 * comment, it's the variable name where the data is stored
 * 
 * @author Terry Wong
 */
public class StoreCode extends ByteCode {

    private int n, storedNum;
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
        storedNum = vm.storeRunStack(n);
    }

    public int getValue() {
        return n;
    }

    public int getStoredNum() {
        return storedNum;
    }

    public String getID() {
        return id;
    }
}
