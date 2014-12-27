/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVirtualMachine;

/**
 * PopCode in Debug mode.
 * Delete the last n items entered into the Symbol Table.
 * 
 * @author Terry Wong
 */
public class DebugPopCode extends PopCode {

    @Override
    public void execute(VirtualMachine vm) {
        vm.popRunStack(n);
        DebugVirtualMachine dvm = (DebugVirtualMachine) vm;
        dvm.popSymTab(n);
    }
}
