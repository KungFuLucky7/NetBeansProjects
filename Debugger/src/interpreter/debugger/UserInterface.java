package interpreter.debugger;

import interpreter.Program;
import interpreter.debugger.UI.ArgsCommand;
import interpreter.debugger.UI.Command;
import interpreter.debugger.UI.RunCommand;
import interpreter.debugger.UI.SetCommand;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * After the Interpreter starts executing in debugging mode, this UserInterface
 * allows the user to access all of the debugger commands to set breakpoints,
 * etc. It calls DebugVirtualMachine for executing bytecodes.
 *
 * @author Terry Wong
 */
public class UserInterface {

    private static boolean isRunning;
    private String input;
    private static DebugVirtualMachine vm;

    public UserInterface(Program p) {
        vm = new DebugVirtualMachine(p);
    }

    public void debugProgram() {
        vm.displaySourceCode();
        isRunning = true;
        while (isRunning) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Type ? for help");
            System.out.print(">>");
            try {
                String UserInput = br.readLine();
                StringTokenizer st = new StringTokenizer(UserInput);
                String commandClass = DebugCodeTable.getCommand(st.nextToken().toLowerCase());
                Command prompt = (Command) (Class.forName("interpreter.debugger.UI." + commandClass)).newInstance();
                if (prompt instanceof SetCommand) {
                    prompt.execute(vm);
                } else if (prompt instanceof RunCommand) {
                    prompt.execute(vm);
                    vm.executeProgram();
                } else if (prompt instanceof ArgsCommand) {
                    ArrayList<String> args = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        args.add(st.nextToken());
                    }
                    if (args.isEmpty()) {
                        throw new IOException();
                    }
                    ArgsCommand Aprompt = (ArgsCommand) prompt;
                    Aprompt.execute(vm, args);
                }
            } catch (Exception e) {
                System.out.println("****Incorrect Command**** " + e);
            }
        }
    }

    public static void exit() {
        isRunning = false;
    }
}
