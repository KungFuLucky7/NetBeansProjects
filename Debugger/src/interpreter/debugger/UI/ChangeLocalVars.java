package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;
import java.util.ArrayList;

/**
 * ChangeLocalVars: Change values of local variables
 * 
 * @author Terry Wong
 */
public class ChangeLocalVars extends ArgsCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
    }

    @Override
    public void execute(DebugVirtualMachine vm, ArrayList<String> args) {
        vm.changeLocalVars(args);
    }
}
