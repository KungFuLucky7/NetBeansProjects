package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;
import java.util.ArrayList;

/**
 * An abstract class for all of the debugger's commands with arguments
 * 
 * @author Terry Wong
 */
public abstract class ArgsCommand extends Command {

    @Override
    public abstract void execute(DebugVirtualMachine vm);

    public abstract void execute(DebugVirtualMachine vm, ArrayList<String> args);
}
