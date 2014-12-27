package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/**
 * POP n: Pop top n levels of runtime stack
 * 
 * @author Terry Wong
 */
public class PopCode extends ByteCode {

    private int n;

    @Override
    public void init(ArrayList<String> args) {
        n = Integer.parseInt(args.get(0));
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.popRunStack(n);
    }
}
