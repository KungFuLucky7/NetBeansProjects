package interpreter.bytecode;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVirtualMachine;

/**
 * CallCode in Debug mode.
 * 
 * @author Terry Wong
 */
public class DebugCallCode extends CallCode {

    @Override
    public void execute(VirtualMachine vm) {
        argsNum = vm.getNumArgs();
        args = vm.peekArgsRunStack(argsNum);
        vm.saveRtAddrs();
        vm.setPC(addrs - 1);
        DebugVirtualMachine dvm = (DebugVirtualMachine) vm;
        if (dvm.getIsTraceOn()) {
            int i = dvm.incrementTraceCounter();
            while (i > 0) {
                System.out.print(" ");
                i--;
            }
            if (funcname != null) {
                i = 0;
                while (i < funcname.length() && funcname.charAt(i) != '<') {
                    System.out.print(funcname.charAt(i));
                    i++;
                }
                if (args != null) {
                    System.out.println("(" + args + ")");
                } else {
                    System.out.println("()");
                }
            }
        }
    }
}