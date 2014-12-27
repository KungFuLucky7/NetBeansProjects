package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/** 
 * LIT n - load the literal value n
 * LIT 0 i - this form of the Lit was generated to load 0 on
 * the stack in order to initialized the variable i to 0 and
 * reserve space on the runtime stack for i
 * 
 * @author Terry Wong
 */
public class LitCode extends ByteCode {

    protected Integer litValue;
    protected String id;

    @Override
    public void init(ArrayList<String> args) {
        if (args.size() == 1) {
            litValue = Integer.parseInt(args.get(0));
        } else {
            litValue = 0;
            id = args.get(1);
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.pushRunStack(litValue);
    }

    public int getValue() {
        return litValue;
    }

    public String getID() {
        return id;
    }
}