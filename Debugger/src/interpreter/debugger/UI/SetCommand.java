package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;

/**
 * An abstract class for all of the debugger's set commands
 * 
 * @author Terry Wong
 */
public abstract class SetCommand extends Command {

    @Override
    public abstract void execute(DebugVirtualMachine vm);
}
