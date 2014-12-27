package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;

/**
 * Trace: Set the trace function on or off
 * if Trace is set on, then print trace information on function entry/exit
 * 
 * @author Terry Wong
 */
public class Trace extends SetCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
        if (!vm.getIsTraceOn()) {
            vm.setIsTraceOn();
            System.out.println("****Trace is on****");
        } else {
            vm.clearIsTraceOn();
            System.out.println("****Trace is off****");
        }
    }
}