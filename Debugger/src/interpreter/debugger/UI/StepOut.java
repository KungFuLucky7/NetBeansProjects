/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;

/**
 * StepOut: Step out of current activation of a function call
 * 
 * @author Terry Wong
 */
public class StepOut extends RunCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
        vm.setIsSetStepOut();
        vm.setStepEnvSize(vm.getEnvSize());
    }
}
