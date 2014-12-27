package interpreter.debugger;

import interpreter.ByteCodeLoader;
import interpreter.Program;
import interpreter.bytecode.ByteCode;
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;

/**
 * The DebugByteCodeLoader (BCL) reads in the next bytecode Builds an instance
 * of the class corresponding to the bytecode The bytecode class instance is
 * added to the Program After all bytecode are loaded, the symbolic addresses
 * are resolved savedSourceFile contains all of the source codes.
 *
 * @author Terry Wong
 */
public class DebugByteCodeLoader extends ByteCodeLoader {

    private BufferedReader sourceFile;
    private BufferedReader sourceProgram;
    private String nextLine;
    public static ArrayList<sourceFile> savedSourceFile = new ArrayList<sourceFile>();

    public DebugByteCodeLoader(String programFile) throws IOException {
        System.out.println("****Debugging File: " + programFile + "****");
        System.out.println("user.dir: " + System.getProperty("user.dir"));
        sourceFile = new BufferedReader(new FileReader(programFile + ".x"));
        sourceProgram = new BufferedReader(new FileReader(programFile + ".x.cod"));
    }

    @Override
    public interpreter.Program loadCodes() {
        Program program = new Program();
        try {
            do {
                nextLine = sourceFile.readLine();
                if (nextLine != null) {
                    savedSourceFile.add(new sourceFile(nextLine));
                }
            } while (nextLine != null);
            do {
                lineno++;
                nextLine = sourceProgram.readLine();
                StringTokenizer st = new StringTokenizer(nextLine);
                String codeClass = DebugCodeTable.get(st.nextToken());
                ArrayList<String> args = new ArrayList<String>();
                while (st.hasMoreTokens()) {
                    args.add(st.nextToken());
                }
                ByteCode bytecode = (ByteCode) (Class.forName("interpreter.bytecode." + codeClass)).newInstance();
                bytecode.init(args);
                program.addCode(bytecode);
            } while (nextLine != null);
        } catch (Exception e) {
        }
        program.ResolveAddrs();
        return program;
    }
}
