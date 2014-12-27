package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * READ; Read an integer; prompt the user for input; put the
 * value just read on top of the stack
 * 
 * @author Terry Wong
 */
public class ReadCode extends ByteCode {

    private int input;

    @Override
    public void init(ArrayList<String> args) {
    }

    @Override
    public void execute(VirtualMachine vm) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter an Integer");
        try {
            String UserInput = br.readLine();
            input = Integer.parseInt(UserInput);
            vm.pushRunStack(input);
        } catch (IOException e) {
            System.out.println("**** " + e);
        }
    }
}
