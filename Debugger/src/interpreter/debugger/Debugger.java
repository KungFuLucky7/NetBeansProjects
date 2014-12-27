package interpreter.debugger;

import interpreter.Interpreter;
import interpreter.Program;
import java.io.*;

/**
 * Provide the debugging operations when interpreter debug switch is on.
 * 
 * @author Terry Wong
 */
public class Debugger extends Interpreter {

    DebugByteCodeLoader dbcl;

    public Debugger() {
    }

    public Debugger(String codeFile) {
        try {
            DebugCodeTable.init();
            dbcl = new DebugByteCodeLoader(codeFile);
        } catch (IOException e) {
            System.out.println("**** " + e);
        }
    }

    public void run() {
        Program program = dbcl.loadCodes();
        UserInterface ui = new UserInterface(program);
        ui.debugProgram();
    }
}
