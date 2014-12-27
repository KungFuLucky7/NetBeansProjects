/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;

/**
 * StepOver: Step over a line
 * 
 * @author Terry Wong
 */
public class StepOver extends RunCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
        vm.setIsSetStepOver();
        vm.setStepEnvSize(vm.getEnvSize());
    }
}
