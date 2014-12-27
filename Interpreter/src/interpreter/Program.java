package interpreter;

import interpreter.bytecode.ByteCode;
import interpreter.bytecode.LabelCode;
import interpreter.bytecode.BranchingCode;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Program class will hold the bytecode program loaded from
 * file; It will also resolve symbolic addresses in the program
 * 
 * @author Terry Wong
 */
public class Program extends Object {

    private ArrayList<ByteCode> program;
    private static HashMap<String, Integer> labels = new HashMap<String, Integer>();

    public Program() {
        program = new ArrayList<ByteCode>();
    }

    public boolean addCode(ByteCode bytecode) {
        if (bytecode instanceof LabelCode) {
            LabelCode lbc = (LabelCode) bytecode;
            labels.put(lbc.getLabel(), ByteCodeLoader.getLineno());
        }
        return program.add(bytecode);
    }

    public ByteCode getCode(int pc) {
        return program.get(pc);
    }

    public void ResolveAddrs() {
        for (int i = 0; i < program.size(); i++) {
            ByteCode bc = program.get(i);
            if (bc instanceof BranchingCode) {
                BranchingCode rac = (BranchingCode) bc;
                if (rac.getLabel() != null) {
                    rac.ConvertAddrs(labels.get(rac.getLabel()));
                }
            }
        }
    }
}