package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/** halt execution
 *
 * @author Terry Wong
 */
public class HaltCode extends ByteCode {

    @Override
    public void init(ArrayList<String> args) {
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.exit();
    }
}
