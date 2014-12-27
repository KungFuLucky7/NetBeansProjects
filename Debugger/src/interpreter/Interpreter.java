package interpreter;

import interpreter.debugger.Debugger;
import java.io.IOException;

/**
 * <pre>
 * 
 *  
 *   
 *     Interpreter class runs the interpreter:
 *     1. Perform all initializations
 *     2. Load the bytecodes from file
 *     3. Run the virtual machine
 *     4. Use an interpreter debug switch for Debugger mode
 *   
 *  
 * </pre>
 */
public class Interpreter {

    ByteCodeLoader bcl;

    public Interpreter() {
    }

    public Interpreter(String codeFile) {
        try {
            CodeTable.init();
            bcl = new ByteCodeLoader(codeFile);
        } catch (IOException e) {
            System.out.println("**** " + e);
        }
    }

    void run() {
        Program program = bcl.loadCodes();
        VirtualMachine vm = new VirtualMachine(program);
        vm.executeProgram();
    }

    public static void main(String args[]) {
        if (args.length == 0) {
            System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");
            System.exit(1);
        }
        if (args[0].equals("-d")) {
            (new Debugger(args[1])).run();
        } else {
            (new Interpreter(args[0])).run();
        }
    }
}