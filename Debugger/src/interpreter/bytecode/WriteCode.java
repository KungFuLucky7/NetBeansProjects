package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/**
 * WRITE: Write the value on top of the stack to output;
 * leave the value on top of the stack
 * 
 * @author Terry Wong
 */
public class WriteCode extends ByteCode {

    @Override
    public void init(ArrayList<String> args) {
    }

    @Override
    public void execute(VirtualMachine vm) {
        if (vm.sizeofRunStack() != 0) {
            System.out.println(vm.peekRunStack());
        }
    }
}
