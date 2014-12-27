/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVirtualMachine;

/**
 * LitCode in Debug mode.
 * Enter a new variable in the Symbol Table.
 * 
 * @author Terry Wong
 */
public class DebugLitCode extends LitCode {

    @Override
    public void execute(VirtualMachine vm) {
        vm.pushRunStack(litValue);
        if (id != null) {
            DebugVirtualMachine dvm = (DebugVirtualMachine) vm;
            dvm.enterSymTab(id, vm.sizeofRunStack() - 1 - vm.getCurrentFrameRunStack());
        }
    }
}
