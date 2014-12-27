package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;

/**
 * An abstract class for all of the debugger commands
 * 
 * @author Terry Wong
 */
public abstract class Command {

    public abstract void execute(DebugVirtualMachine vm);
}
