package interpreter.debugger;

import java.util.ArrayList;

/**
 * The FunctionEnvironmentRecord contains information for a
 * frame (a function environment record).
 * It uses the Symbol Table mechanism similar to the one used by the constrainer.
 * 
 * @author Terry Wong
 */
class Binder {

    private int value;
    private String prevtop;   // prior symbol in same scope
    private Binder tail;      // prior binder for same symbol
    // restore this when closing scope

    Binder(int v, String p, Binder t) {
        value = v;
        prevtop = p;
        tail = t;
    }

    int getValue() {
        return value;
    }

    String getPrevtop() {
        return prevtop;
    }

    Binder getTail() {
        return tail;
    }
}

/** <pre>
 * The Table class is similar to java.util.Dictionary, except that
 * each key must be a String and there is a scope mechanism.
 *</pre>
 */
class Table {

    private java.util.HashMap<String, Binder> symbols = new java.util.HashMap<String, Binder>();
    private String top;    // reference to last symbol added to
    // current scope; this essentially is the
    // start of a linked list of symbols in scope
    private Binder marks;  // scope mark; essentially we have a stack of
    // marks - push for new scope; pop when closing scope

    public Table() {
    }

    /**
     * Gets the int associated with the specified symbol in the Table.
     */
    public int get(String key) {
        Binder e = symbols.get(key);
        return e.getValue();
    }

    /**
     * Puts the specified value into the Table, bound to the specified String.<br>
     * Maintain the list of symbols in the current scope (top);<br>
     * Add to list of symbols in prior scope with the same string identifier
     */
    public void put(String key, int value) {
        symbols.put(key, new Binder(value, top, symbols.get(key)));
        top = key;
    }

    /**
     * Remembers the current state of the Table; push new mark on mark stack
     */
    public void beginScope() {
        marks = new Binder(-1, top, marks);
        top = null;
    }

    /**
     * Removes the last n items entered into the Symbol Table
     */
    public void remove(int n) {
        while (n != 0) {
            if (top != null) {
                Binder e = symbols.get(top);
                if (e.getTail() != null) {
                    symbols.put(top, e.getTail());
                } else {
                    symbols.remove(top);
                }
                if (e.getPrevtop() != null) {
                    top = e.getPrevtop();
                }
            }
            n--;
        }
    }

    /**
     * Returns the size of the Symbol Table
     */
    public int sizeof() {
        return symbols.size();
    }

    /**
     * Restores the table to what it was at the most recent beginScope
     *	that has not already been ended.
     */
    public void endScope() {
        while (top != null) {
            Binder e = symbols.get(top);
            if (e.getTail() != null) {
                symbols.put(top, e.getTail());
            } else {
                symbols.remove(top);
            }
            top = e.getPrevtop();
        }
        if (marks != null) {
            top = marks.getPrevtop();
            marks = marks.getTail();
        }
    }

    public void displaySymTab() {
        int counter = 1;
        for (String key : symbols.keySet()) {
            if (counter == symbols.size()) {
                System.out.print("<" + key + ","
                        + symbols.get(key).getValue() + ">)");
            } else {
                System.out.print("<" + key + ","
                        + symbols.get(key).getValue() + ">,");
            }
            counter++;
        }
    }

    public java.util.HashMap<String, Binder> getSymTab() {
        return symbols;
    }

    /**
     * @return a set of the Table's symbols.
     */
    public java.util.Set<String> keys() {
        return symbols.keySet();
    }
}

public class FunctionEnvironmentRecord {

    private Table table;
    private int startLine, endLine, currentLine;
    private String funcname;
    private ArrayList<Integer> parameterList;

    public FunctionEnvironmentRecord() {
        table = new Table();
        parameterList = new ArrayList<Integer>();
    }

    public void BeginScope() {
        table.beginScope();
    }

    public void Function(String fname, int sLine, int eLine) {
        funcname = fname;
        startLine = sLine;
        endLine = eLine;
    }

    public void Line(int n) {
        currentLine = n;
    }

    public void Enter(String s, int v) {
        table.put(s, v);
    }

    public void Pop(int n) {
        table.remove(n);
    }

    public int getValue(String key) {
        return table.get(key);
    }

    public String getFuncname() {
        return funcname;
    }

    public void setFuncname(String fname) {
        funcname = fname;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int i) {
        startLine = i;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int i) {
        endLine = i;
    }

    public void setParameterList(ArrayList<Integer> n) {
        parameterList = n;
    }

    public ArrayList<Integer> getParameterList() {
        return parameterList;
    }

    public int getSizeof() {
        return table.sizeof();
    }

    public void Dump() {
        System.out.print("<");
        if (table.sizeof() != 0) {
            System.out.print("(");
            table.displaySymTab();
            System.out.print(",");
        } else if (funcname != null) {
            System.out.print("(),");
        } else {
            System.out.print("-,");
        }
        if (startLine > 0) {
            System.out.print(startLine + ",");
        } else {
            System.out.print("-,");
        }
        if (endLine > 0) {
            System.out.print(endLine + ",");
        } else {
            System.out.print("-,");
        }
        if (funcname != null) {
            System.out.print(funcname + ",");
        } else {
            System.out.print("-,");
        }
        if (currentLine > 0) {
            System.out.println(currentLine + ">");
        } else {
            System.out.println("->");
        }
    }

    public java.util.HashMap<String, Binder> getSymTab() {
        return table.getSymTab();
    }

    public static void main(String args[]) {
        FunctionEnvironmentRecord fctEnvRecord = new FunctionEnvironmentRecord();
        fctEnvRecord.BeginScope();
        fctEnvRecord.Dump();
        fctEnvRecord.Function("g", 1, 20);
        fctEnvRecord.Dump();
        fctEnvRecord.Line(5);
        fctEnvRecord.Dump();
        fctEnvRecord.Enter("a", 4);
        fctEnvRecord.Dump();
        fctEnvRecord.Enter("b", 2);
        fctEnvRecord.Dump();
        fctEnvRecord.Enter("c", 7);
        fctEnvRecord.Dump();
        fctEnvRecord.Enter("a", 1);
        fctEnvRecord.Dump();
        fctEnvRecord.Pop(2);
        fctEnvRecord.Dump();
        fctEnvRecord.Pop(1);
        fctEnvRecord.Dump();
    }
}
