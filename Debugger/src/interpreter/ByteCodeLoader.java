package interpreter;

import interpreter.bytecode.ByteCode;
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class ByteCodeLoader extends Object {

    /**
     * The ByteCodeLoader (BCL) reads in the next bytecode Builds an instance of
     * the class corresponding to the bytecode The bytecode class instance is
     * added to the Program After all bytecode are loaded, the symbolic
     * addresses are resolved
     */
    private BufferedReader source;
    protected static int lineno = -1;
    private String nextLine;
    private static ArrayList<String> savedSourceFile = new ArrayList<String>();

    /**
     * Construct a new SourceReader
     *
     * @param sourceFile the String describing the user's source file
     * @exception IOException is thrown if there is an I/O problem
     */
    public ByteCodeLoader() {
    }

    public ByteCodeLoader(String programFile) throws IOException {
        System.out.println("Source file: " + programFile);
        System.out.println("user.dir: " + System.getProperty("user.dir"));
        System.out.println();
        source = new BufferedReader(new FileReader(programFile));
    }

    void close() {
        try {
            source.close();
        } catch (Exception e) {
            System.out.println("**** " + e);
        }
    }

    /**
     * Load all the codes from file; request Program to resolve any branch
     * addresses from symbols to their address in code memory.
     */
    public interpreter.Program loadCodes() {
        Program program = new Program();
        try {
            do {
                lineno++;
                nextLine = source.readLine();
                StringTokenizer st = new StringTokenizer(nextLine);
                if (nextLine != null) {
                    savedSourceFile.add(nextLine);
                }
                String codeClass = CodeTable.get(st.nextToken());
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

    public static int getLineno() {
        return lineno;
    }

    public static ArrayList<String> getSavedSourceFile() {
        return savedSourceFile;
    }
}
