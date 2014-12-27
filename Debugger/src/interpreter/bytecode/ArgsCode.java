package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/** 
 * ARGS n;Used prior to calling a function:
 * n = #args
 * this instruction is immediately followed by the CALL instruction;
 * the function has n args so ARGS n instructs the interpreter to set
 * up a new frame n down from the top, so it will include the arguments
 *
 * @author Terry Wong
 */
public class ArgsCode extends ByteCode {

    protected int numArgs = 0;

    @Override
    public void init(ArrayList<String> args) {
        if (!args.isEmpty()) {
            numArgs = Integer.parseInt(args.get(0));
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        if (numArgs != 0) {
            vm.newFrameAtRunStack(vm.sizeofRunStack() - numArgs);
        } else {
            vm.newFrameAtRunStack(vm.sizeofRunStack());
        }
        vm.setNumArgs(numArgs);
    }
}