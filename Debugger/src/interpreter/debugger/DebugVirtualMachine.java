package interpreter.debugger;

import interpreter.Program;
import interpreter.RunTimeStack;
import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import java.io.IOException;
import java.util.*;

/**
 * The DebugVirtualMachine operates with runStack when it gets called by the
 * UserInterface The returnAddrs stack stores the bytecode index (PC) that the
 * debug virtual machine should execute when the current function exits. Each
 * time a function is entered, the PC should be pushed on to the returnAddrs
 * stack. When a function exits the PC should be restored to the value that is
 * popped from the top of the returnAddrs stack.
 *
 * @author Terry Wong
 */
public class DebugVirtualMachine extends VirtualMachine {

    private Program program;
    private Stack<FunctionEnvironmentRecord> environmentStack;
    private int envSize, stepEnvSize, traceCounter;
    private boolean isSetStepOver = false, isSetStepInto = false, isSetStepOut = false, isRollBack = false, isTraceOn = false;
    public static SortedSet<Integer> Breakpts = new TreeSet<Integer>();

    public DebugVirtualMachine(Program p) {
        pc = 0;
        program = p;
        runStack = new RunTimeStack();
        returnAddrs = new Stack<Integer>();
        environmentStack = new Stack<FunctionEnvironmentRecord>();
        environmentStack.push(new FunctionEnvironmentRecord());
        envSize = environmentStack.size();
        stepEnvSize = 1;
        traceCounter = 0;
        environmentStack.lastElement().Line(-1);
    }

    @Override
    public void executeProgram() {
        isRunning = true;
        while (isRunning) {
            ByteCode code = program.getCode(pc);
            code.execute(this);
            pc++;
        }
        displaySourceCode();
    }

    public void setParameterListFER(int n) {
        environmentStack.lastElement().setParameterList(runStack.peekArgsInt(n));
    }

    public void restoreArgsToRunStack() {
        for (int i = 0; i < environmentStack.lastElement().getParameterList().size(); i++) {
            runStack.push(environmentStack.lastElement().getParameterList().get(i));
        }
    }

    public void restoreRollBackAddrs() {
        if (!returnAddrs.isEmpty()) {
            pc = returnAddrs.pop() - 1;
        } else {
            pc = 0;
        }
    }

    public int getCurrentLine() {
        return environmentStack.lastElement().getCurrentLine();
    }

    public void setCurrentLine(int n) {
        environmentStack.lastElement().Line(n);
    }

    public int getStartLine() {
        return environmentStack.lastElement().getStartLine();
    }

    public int getEndLine() {
        return environmentStack.lastElement().getEndLine();
    }

    public void enterSymTab(String k, int n) {
        environmentStack.lastElement().Enter(k, n);
    }

    public void popSymTab(int n) {
        environmentStack.lastElement().Pop(n);
    }

    public void setFunction(String fname, int sLine, int eLine) {
        environmentStack.lastElement().Function(fname, sLine, eLine);
    }

    public String getFunctionName() {
        return environmentStack.lastElement().getFuncname();
    }

    public void newFrameAtFunctionEnvironmentRecord() {
        environmentStack.push(new FunctionEnvironmentRecord());
        environmentStack.lastElement().BeginScope();
    }

    public void popFrameFunctionEnvironmentRecord() {
        environmentStack.pop();
        envSize = environmentStack.size();
    }

    public void renewFunctionEnvironmentRecord() {
        while (runStack.sizeof() != 0) {
            runStack.pop();
        }
        while (!environmentStack.isEmpty()) {
            environmentStack.pop();
        }
        environmentStack.push(new FunctionEnvironmentRecord());
        envSize = environmentStack.size();
        setCurrentLine(-1);
    }

    public boolean isEnvSizeTheSame() {
        if (envSize == environmentStack.size()) {
            return true;
        } else {
            return false;
        }
    }

    public int getEnvSize() {
        return environmentStack.size();
    }

    public void setEnvSize() {
        envSize = environmentStack.size();
    }

    public int getStepEnvSize() {
        return stepEnvSize;
    }

    public void setStepEnvSize(int n) {
        stepEnvSize = n;
    }

    public String BreakptsToString() {
        String BTS = "";
        Iterator i = Breakpts.iterator();
        while (i.hasNext()) {
            BTS += i.next() + " ";
        }
        return BTS;
    }

    public boolean getIsSetStepOver() {
        return isSetStepOver;
    }

    public void setIsSetStepOver() {
        isSetStepOver = true;
    }

    public void clearIsSetStepOver() {
        isSetStepOver = false;
    }

    public boolean getIsSetStepInto() {
        return isSetStepInto;
    }

    public void setIsSetStepInto() {
        isSetStepInto = true;
    }

    public void clearIsSetStepInto() {
        isSetStepInto = false;
    }

    public boolean getIsSetStepOut() {
        return isSetStepOut;
    }

    public void setIsSetStepOut() {
        isSetStepOut = true;
    }

    public void clearIsSetStepOut() {
        isSetStepOut = false;
    }

    public void clearIsSetFlags() {
        isSetStepOver = false;
        isSetStepInto = false;
        isSetStepOut = false;
    }

    public boolean getIsRollBack() {
        return isRollBack;
    }

    public void setIsRollBack() {
        isRollBack = true;
    }

    public void clearIsRollBack() {
        isRollBack = false;
    }

    public boolean getIsTraceOn() {
        return isTraceOn;
    }

    public void setIsTraceOn() {
        isTraceOn = true;
    }

    public void clearIsTraceOn() {
        isTraceOn = false;
    }

    public int incrementTraceCounter() {
        return traceCounter++;
    }

    public int decrementTraceCounter() {
        return --traceCounter;
    }

    public void displayFunctionRecord() {
        System.out.println("<(<var name, var offset>,...), startline, endline, function, currentline>");
        environmentStack.lastElement().Dump();
    }

    public void displayLocalVars() {
        HashMap<String, Binder> symTab = environmentStack.lastElement().getSymTab();
        if (symTab.isEmpty()) {
            System.out.println("****No local variables loaded****");
        } else {
            int counter = 1;
            for (String key : symTab.keySet()) {
                if (counter == symTab.size()) {
                    System.out.println(key + ": " + runStack.peekElement(symTab.get(key).getValue()) + " ");
                } else {
                    System.out.print(key + ": " + runStack.peekElement(symTab.get(key).getValue()) + " ");
                }
                counter++;
            }
        }
    }

    public void changeLocalVars(ArrayList<String> args) {
        HashMap<String, Binder> symTab = environmentStack.lastElement().getSymTab();
        if (symTab.isEmpty()) {
            System.out.println("****No local variables loaded****");
        } else {
            try {
                if (!symTab.containsKey(args.get(0))) {
                    throw new IOException();
                }
                runStack.changeElement(symTab.get(args.get(0)).getValue(), Integer.parseInt(args.get(1)));
                System.out.println("Local variable " + args.get(0) + " was changed to: " + runStack.peekElement(symTab.get(args.get(0)).getValue()));
            } catch (IOException e) {
                System.out.println("****Incorrect Command**** " + e);
            }
        }
    }

    public void displaySourceCode() {
        if (environmentStack.lastElement().getFuncname() == null) {
            System.out.println();
            if (DebugByteCodeLoader.savedSourceFile.get(0).BreakptStatus()) {
                System.out.println("** " + 1 + ". " + DebugByteCodeLoader.savedSourceFile.get(0).toString() + "     <-----");
            } else {
                System.out.println("   " + 1 + ". " + DebugByteCodeLoader.savedSourceFile.get(0).toString() + "     <-----");
            }
            for (int i = 1; i < DebugByteCodeLoader.savedSourceFile.size(); i++) {
                if (i < 9) {
                    if (DebugByteCodeLoader.savedSourceFile.get(i).BreakptStatus()) {
                        System.out.println("** " + (i + 1) + ". " + DebugByteCodeLoader.savedSourceFile.get(i).toString());
                    } else {
                        System.out.println("   " + (i + 1) + ". " + DebugByteCodeLoader.savedSourceFile.get(i).toString());
                    }
                } else {
                    if (DebugByteCodeLoader.savedSourceFile.get(i).BreakptStatus()) {
                        System.out.println("**" + (i + 1) + ". " + DebugByteCodeLoader.savedSourceFile.get(i).toString());
                    } else {
                        System.out.println("  " + (i + 1) + ". " + DebugByteCodeLoader.savedSourceFile.get(i).toString());
                    }
                }
            }
            System.out.println();
        } else if (environmentStack.lastElement().getCurrentLine() != -1) {
            System.out.println();
            for (int i = environmentStack.lastElement().getStartLine(); i <= environmentStack.lastElement().getEndLine(); i++) {
                if (i < 10) {
                    if (DebugByteCodeLoader.savedSourceFile.get(i - 1).BreakptStatus() && i == environmentStack.lastElement().getCurrentLine()) {
                        System.out.println("** " + i + ". " + DebugByteCodeLoader.savedSourceFile.get(i - 1).toString() + "     <-----");
                    } else if (!DebugByteCodeLoader.savedSourceFile.get(i - 1).BreakptStatus() && i == environmentStack.lastElement().getCurrentLine()) {
                        System.out.println("   " + i + ". " + DebugByteCodeLoader.savedSourceFile.get(i - 1).toString() + "     <-----");
                    } else if (DebugByteCodeLoader.savedSourceFile.get(i - 1).BreakptStatus() && i != environmentStack.lastElement().getCurrentLine()) {
                        System.out.println("** " + i + ". " + DebugByteCodeLoader.savedSourceFile.get(i - 1).toString());
                    } else {
                        System.out.println("   " + i + ". " + DebugByteCodeLoader.savedSourceFile.get(i - 1).toString());
                    }
                } else {
                    if (DebugByteCodeLoader.savedSourceFile.get(i - 1).BreakptStatus() && i == environmentStack.lastElement().getCurrentLine()) {
                        System.out.println("**" + i + ". " + DebugByteCodeLoader.savedSourceFile.get(i - 1).toString() + "     <-----");
                    } else if (!DebugByteCodeLoader.savedSourceFile.get(i - 1).BreakptStatus() && i == environmentStack.lastElement().getCurrentLine()) {
                        System.out.println("  " + i + ". " + DebugByteCodeLoader.savedSourceFile.get(i - 1).toString() + "     <-----");
                    } else if (DebugByteCodeLoader.savedSourceFile.get(i - 1).BreakptStatus() && i != environmentStack.lastElement().getCurrentLine()) {
                        System.out.println("**" + i + ". " + DebugByteCodeLoader.savedSourceFile.get(i - 1).toString());
                    } else {
                        System.out.println("  " + i + ". " + DebugByteCodeLoader.savedSourceFile.get(i - 1).toString());
                    }
                }
            }
            System.out.println();
        } else if (environmentStack.lastElement().getFuncname().equalsIgnoreCase("Read")) {
            System.out.println();
            System.out.println("****READ****");
            System.out.println();
        } else if (environmentStack.lastElement().getFuncname().equalsIgnoreCase("Write")) {
            System.out.println();
            System.out.println("****WRITE****");
            System.out.println();
        }
    }

    public void reexecuteFunction() {
        setIsRollBack();
        if (environmentStack.size() == 1) {
            renewFunctionEnvironmentRecord();
            pc = 0;
        } else {
            runStack.push(0);
            popFrameRunStack();
            runStack.pop();
            restoreArgsToRunStack();
            popFrameFunctionEnvironmentRecord();
            restoreRollBackAddrs();
        }
        System.out.println("****Re-execute current function****");
    }

    public void breakOp() {
        isRunning = false;
    }
}
