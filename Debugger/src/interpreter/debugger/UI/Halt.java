package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;
import interpreter.debugger.UserInterface;

/**
 * Halt: Halt execution
 * 
 * @author Terry Wong
 */
public class Halt extends SetCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
        System.out.println("***************Execution Halted***************");
        UserInterface.exit();
    }
}
