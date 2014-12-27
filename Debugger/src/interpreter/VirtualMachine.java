package interpreter;

import interpreter.bytecode.*;
import java.util.Stack;

/**
 * The returnAddrs stack stores the bytecode index (PC) that the virtual machine
 * should execute when the current function exits. Each time a function is entered,
 * the PC should be pushed on to the returnAddrs stack. When a function exits the PC
 * should be restored to the value that is popped from the top of the returnAddrs stack.
 * 
 * @author Terry Wong
 */
public class VirtualMachine {

    protected RunTimeStack runStack;
    protected int pc, numArgs = 0;
    protected Stack<Integer> returnAddrs;
    protected static boolean isRunning;
    private Program program;
    private static boolean dumpFlag = false;

    public VirtualMachine() {
    }

    public VirtualMachine(Program p) {
        program = p;
    }

    public void executeProgram() {
        pc = 0;
        runStack = new RunTimeStack();
        returnAddrs = new Stack<Integer>();
        isRunning = true;
        while (isRunning) {
            ByteCode code = program.getCode(pc);
            int currentPC = pc;
            code.execute(this);
            if (dumpFlag == true) {
                if (!(code instanceof DumpCode)) {
                    if (code instanceof CallCode) {
                        CallCode cc = (CallCode) code;
                        if (cc.getLabel() != null) {
                            System.out.print("CALL " + cc.getLabel() + "\t");
                            int i = 0;
                            while (i < cc.getLabel().length() && cc.getLabel().charAt(i) != '<') {
                                System.out.print(cc.getLabel().charAt(i));
                                i++;
                            }
                            if (cc.getArgs() != null) {
                                System.out.println("(" + cc.getArgs() + ")");
                            } else {
                                System.out.println("()");
                            }
                        } else {
                            System.out.println(ByteCodeLoader.getSavedSourceFile().get(currentPC));
                        }
                    } else if (code instanceof ReturnCode) {
                        ReturnCode rc = (ReturnCode) code;
                        if (rc.getLabel() != null) {
                            System.out.print("RETURN " + rc.getLabel() + "\texit ");
                            int i = 0;
                            while (i < rc.getLabel().length() && rc.getLabel().charAt(i) != '<') {
                                System.out.print(rc.getLabel().charAt(i));
                                i++;
                            }
                            System.out.println(": " + rc.getValue());
                        } else {
                            System.out.println(ByteCodeLoader.getSavedSourceFile().get(currentPC));
                        }
                    } else if (code instanceof LitCode) {
                        LitCode lic = (LitCode) code;
                        if (lic.getID() != null) {
                            System.out.println("LIT " + lic.getValue() + " " + lic.getID()
                                    + "\t\tint " + lic.getID());
                        } else {
                            System.out.println(ByteCodeLoader.getSavedSourceFile().get(currentPC));
                        }
                    } else if (code instanceof LoadCode) {
                        LoadCode lc = (LoadCode) code;
                        if (lc.getID() != null) {
                            System.out.println("LOAD " + lc.getValue() + " " + lc.getID()
                                    + "\t<load " + lc.getID() + ">");
                        } else {
                            System.out.println(ByteCodeLoader.getSavedSourceFile().get(currentPC));
                        }
                    } else if (code instanceof StoreCode) {
                        StoreCode sc = (StoreCode) code;
                        if (sc.getID() != null) {
                            System.out.println("STORE " + sc.getValue() + " " + sc.getID()
                                    + "\t" + sc.getID() + " = " + sc.getStoredNum());
                        } else {
                            System.out.println(ByteCodeLoader.getSavedSourceFile().get(currentPC));
                        }
                    } else {
                        System.out.println(ByteCodeLoader.getSavedSourceFile().get(currentPC));
                    }
                    runStack.dump(); //check that the operation is correct
                }
            }
            pc++;
        }
    }

    public void dumpRunStack() {
        runStack.dump();
    }

    public int peekRunStack() {
        return runStack.peek();
    }

    public String peekArgsRunStack(int n) {
        return runStack.peekArgs(n);
    }

    public void setNumArgs(int n) {
        numArgs = n;
    }

    public int getNumArgs() {
        return numArgs;
    }

    public int getCurrentFrameRunStack() {
        return runStack.getCurrentFrame();
    }

    public int popRunStack() {
        return runStack.pop();
    }

    public void popRunStack(int i) {
        runStack.pop(i);
    }

    public int pushRunStack(int i) {
        return runStack.push(i);
    }

    public void newFrameAtRunStack(int offset) {
        runStack.newFrameAt(offset);
    }

    public int popFrameRunStack() {
        return runStack.popFrame();
    }

    public int storeRunStack(int offset) {
        return runStack.store(offset);
    }

    public int loadRunStack(int offset) {
        return runStack.load(offset);
    }

    public void saveRtAddrs() {
        returnAddrs.push(pc);
    }

    public void restoreRtAddrs() {
        if (!returnAddrs.isEmpty()) {
            pc = returnAddrs.pop();
        } else {
            pc = 0;
        }
    }

    public int getPC() {
        return pc;
    }

    public void setPC(int i) {
        pc = i;
    }

    public int sizeofRunStack() {
        return runStack.sizeof();
    }

    public void exit() {
        isRunning = false;
    }

    public void setDumpFlagOn() {
        dumpFlag = true;
    }

    public void setDumpFlagOff() {
        dumpFlag = false;
    }
}
