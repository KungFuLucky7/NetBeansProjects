package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/**
 * bop <binary op> - pop top 2 levels of the stack and
 * perform indicated operation - operations are + - / * == !=
 * <= > >= < | &
 * | and & are logical operators, not bit operators
 * lower level is the first operand:
 * 
 * @author Terry Wong
 */
public class BopCode extends ByteCode {

    private String Op;

    @Override
    public void init(ArrayList<String> args) {
        Op = args.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        int v1, v2;
        if (Op.equals("+")) {
            v2 = vm.popRunStack();
            v1 = vm.popRunStack();
            vm.pushRunStack(v1 + v2);
        }
        if (Op.equals("-")) {
            v2 = vm.popRunStack();
            v1 = vm.popRunStack();
            vm.pushRunStack(v1 - v2);
        }
        if (Op.equals("/")) {
            v2 = vm.popRunStack();
            v1 = vm.popRunStack();
            assert (v2 != 0);
            vm.pushRunStack(v1 / v2);
        }
        if (Op.equals("*")) {
            v2 = vm.popRunStack();
            v1 = vm.popRunStack();
            vm.pushRunStack(v1 * v2);
        }
        if (Op.equals("==")) {
            v2 = vm.popRunStack();
            v1 = vm.popRunStack();
            if (v1 == v2) {
                vm.pushRunStack(1);
            } else {
                vm.pushRunStack(0);
            }
        }
        if (Op.equals("!=")) {
            v2 = vm.popRunStack();
            v1 = vm.popRunStack();
            if (v1 != v2) {
                vm.pushRunStack(1);
            } else {
                vm.pushRunStack(0);
            }
        }
        if (Op.equals("<=")) {
            v2 = vm.popRunStack();
            v1 = vm.popRunStack();
            if (v1 <= v2) {
                vm.pushRunStack(1);
            } else {
                vm.pushRunStack(0);
            }
        }
        if (Op.equals(">")) {
            v2 = vm.popRunStack();
            v1 = vm.popRunStack();
            if (v1 > v2) {
                vm.pushRunStack(1);
            } else {
                vm.pushRunStack(0);
            }
        }
        if (Op.equals(">=")) {
            v2 = vm.popRunStack();
            v1 = vm.popRunStack();
            if (v1 >= v2) {
                vm.pushRunStack(1);
            } else {
                vm.pushRunStack(0);
            }
        }
        if (Op.equals("<")) {
            v2 = vm.popRunStack();
            v1 = vm.popRunStack();
            if (v1 < v2) {
                vm.pushRunStack(1);
            } else {
                vm.pushRunStack(0);
            }
        }
        if (Op.equals("|")) {
            v2 = vm.popRunStack();
            v1 = vm.popRunStack();
            if (v1 == 0 | v2 == 0) {
                vm.pushRunStack(0);
            } else {
                vm.pushRunStack(1);
            }
        }
        if (Op.equals("&")) {
            v2 = vm.popRunStack();
            v1 = vm.popRunStack();
            if (v1 == 0 & v2 == 0) {
                vm.pushRunStack(0);
            } else {
                vm.pushRunStack(1);
            }
        }
    }
}
