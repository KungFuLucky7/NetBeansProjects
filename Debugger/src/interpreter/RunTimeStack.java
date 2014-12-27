/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

/**
 * The RunTimeStack class maintains the stack of active frames; when we call
 * a function we'll push a new frame on the stack; when we return from a function
 * we'll pop the top frame
 *
 * @author Terry Wong
 */
public class RunTimeStack {

    private Stack<Integer> framePointers;
    private Vector<Integer> runStack;

    public RunTimeStack() {
        framePointers = new Stack<Integer>();
        runStack = new Vector<Integer>();
        framePointers.push(0);
    }

    public void dump() {
        if (!runStack.isEmpty()) {
            display(0);
        }
    }

    private void display(int index) {
        if (index != framePointers.size() - 1 && framePointers.get(index) < runStack.size()) {
            System.out.print("[" + runStack.elementAt(framePointers.get(index)));
            for (int i = framePointers.get(index) + 1; i < framePointers.get(index + 1); i++) {
                System.out.print("," + runStack.elementAt(i));
            }
            System.out.print("] ");
            display(index + 1);
        } else if (index == framePointers.size() - 1 && framePointers.get(index) < runStack.size()) {
            System.out.print("[" + runStack.elementAt(framePointers.get(index)));
            for (int i = framePointers.get(index) + 1; i < runStack.size(); i++) {
                System.out.print("," + runStack.elementAt(i));
            }
            System.out.println("]");
        } else {
            System.out.println("[]");
        }
    }

    public int peek() {
        return runStack.lastElement();
    }

    public int peekElement(int n) {
        return runStack.get(framePointers.lastElement() + n);
    }

    public int changeElement(int n, int i) {
        runStack.set(framePointers.lastElement() + n, i);
        return peekElement(n);
    }

    public String peekArgs(int n) {
        String args = "";
        if (n == 0) {
        } else if (n == 1) {
            args += runStack.get(runStack.size() - n);
        } else {
            args += runStack.get(runStack.size() - n);
            n--;
            while (n > 0) {
                args += "," + runStack.get(runStack.size() - n);
                n--;
            }
        }
        return args;
    }

    public ArrayList<Integer> peekArgsInt(int n) {
        ArrayList<Integer> args = new ArrayList<Integer>();
        if (n == 0) {
        } else {
            for (int i = 0; i < n; i++) {
                args.add(runStack.get(runStack.size() - n));
            }
        }
        return args;
    }

    public int getCurrentFrame() {
        return framePointers.lastElement();
    }

    public int pop() {
        return runStack.remove(runStack.size() - 1);
    }

    public void pop(int i) {
        while (i != 0) {
            runStack.remove(runStack.size() - 1);
            i--;
        }
    }

    public int push(int i) {
        runStack.add(new Integer(i));
        return runStack.lastElement();
    }

    public void newFrameAt(int offset) {
        if (offset != 0) {
            framePointers.push(offset);
        }
    }

    public int popFrame() {
        assert !runStack.isEmpty();
        if (framePointers.lastElement() != 0 && framePointers.size() > 1) {
            Integer rtValue = runStack.lastElement();
            int i = framePointers.pop();
            while (i <= runStack.size() - 1) {
                runStack.remove(runStack.size() - 1);
            }
            runStack.add(rtValue);
        } else {
            Integer rtValue = runStack.lastElement();
            int i = framePointers.lastElement();
            while (i <= runStack.size() - 1) {
                runStack.remove(runStack.size() - 1);
            }
            runStack.add(rtValue);
        }
        return runStack.lastElement();
    }

    public int store(int offset) {
        runStack.set(framePointers.lastElement() + offset, runStack.remove(runStack.size() - 1));
        return runStack.get(framePointers.lastElement() + offset);
    }

    public int load(int offset) {
        runStack.add(runStack.get(framePointers.lastElement() + offset));
        return runStack.lastElement();
    }

    public int sizeof() {
        return runStack.size();
    }
}
