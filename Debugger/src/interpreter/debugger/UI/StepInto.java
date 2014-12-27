/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;

/**
 * StepInto: Step into a function call on the current line
 * 
 * @author Terry Wong
 */
public class StepInto extends RunCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
        vm.setIsSetStepInto();
    }
}
