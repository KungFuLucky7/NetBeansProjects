package interpreter.debugger.UI;

import interpreter.debugger.DebugByteCodeLoader;
import interpreter.debugger.DebugVirtualMachine;
import java.io.IOException;
import java.util.ArrayList;

/**
 * SetBreakpoints: Set breakpoints at valid locations in source program
 * 
 * @author Terry Wong
 */
public class SetBreakpoints extends ArgsCommand {

    @Override
    public void execute(DebugVirtualMachine vm) {
    }

    @Override
    public void execute(DebugVirtualMachine vm, ArrayList<String> args) {
        String breakPts = "", nonbreakPts = "";
        try {
            for (int i = 0; i < args.size(); i++) {
                int Index = Integer.parseInt(args.get(i));
                if (Index <= 0 || Index > DebugByteCodeLoader.savedSourceFile.size()) {
                    throw new IOException();
                }
                if (DebugByteCodeLoader.savedSourceFile.get(Index - 1).BreakptSetAvailStatus()) {
                    if (!DebugVirtualMachine.Breakpts.contains(Index)) {
                        DebugVirtualMachine.Breakpts.add(Index);
                        DebugByteCodeLoader.savedSourceFile.get(Index - 1).setBreakpt();
                    }
                    breakPts += args.get(i) + " ";
                } else {
                    nonbreakPts += args.get(i) + " ";
                }
            }
            if (!breakPts.isEmpty()) {
                System.out.println("Breakpoints set: " + breakPts);
            }
            if (!nonbreakPts.isEmpty()) {
                System.out.println("Breakpoints cannot be set: " + nonbreakPts);
            }
        } catch (IOException e) {
            System.out.println("****Incorrect Command**** " + e);
        }
    }
}