package interpreter.debugger.UI;

import interpreter.debugger.DebugByteCodeLoader;
import interpreter.debugger.DebugVirtualMachine;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ClearBreakpoints: Clear designated breakpoints
 * 
 * @author Terry Wong
 */
public class ClearBreakpoints extends ArgsCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
    }

    @Override
    public void execute(DebugVirtualMachine vm, ArrayList<String> args) {
        String clearedbreakPts = "", nonsetbreakPts = "";
        try {
            for (int i = 0; i < args.size(); i++) {
                int Index = Integer.parseInt(args.get(i));
                if (Index <= 0 || Index > DebugByteCodeLoader.savedSourceFile.size()) {
                    throw new IOException();
                }
                if (DebugVirtualMachine.Breakpts.contains(Index)) {
                    DebugVirtualMachine.Breakpts.remove(Index);
                    DebugByteCodeLoader.savedSourceFile.get(Index - 1).clearBreakpt();
                    clearedbreakPts += args.get(i) + " ";
                } else {
                    nonsetbreakPts += args.get(i) + " ";
                }
            }
            if (!clearedbreakPts.isEmpty()) {
                System.out.println("Breakpoints cleared: " + clearedbreakPts);
            }
            if (!nonsetbreakPts.isEmpty()) {
                System.out.println("No breakpoints set: " + nonsetbreakPts);
            }
        } catch (IOException e) {
            System.out.println("****Incorrect Command**** " + e);
        }
    }
}