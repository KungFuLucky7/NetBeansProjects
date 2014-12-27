package interpreter.debugger;

/**
 * This sourceFile class contains a vector that has
 * a line of source code and the breakpoint status
 * 
 * @author Terry Wong
 */
public class sourceFile {

    private String sourceLine;
    private boolean isBreakptSet = false;
    private boolean BreakptSetAvail = false;

    public sourceFile(String source) {
        sourceLine = source;
    }

    @Override
    public String toString() {
        return sourceLine;
    }

    public boolean BreakptSetAvailStatus() {
        return BreakptSetAvail;
    }

    public boolean setBreakptSetAvailStatus() {
        return BreakptSetAvail = true;
    }

    public boolean BreakptStatus() {
        return isBreakptSet;
    }

    public boolean setBreakpt() {
        return isBreakptSet = true;
    }

    public boolean clearBreakpt() {
        return isBreakptSet = false;
    }
}