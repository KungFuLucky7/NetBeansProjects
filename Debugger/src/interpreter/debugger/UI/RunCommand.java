package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;

/**
 * An abstract class for all of the debugger's run commands
 * 
 * @author Terry Wong
 */
public abstract class RunCommand extends Command {

    @Override
    public abstract void execute(DebugVirtualMachine vm);
}
