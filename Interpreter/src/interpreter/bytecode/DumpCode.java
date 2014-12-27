package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.io.IOException;
import java.util.ArrayList;

/**
 * DumpCode is an interpreter command to turn on runtime dumping.
 * 
 * @author Terry Wong
 */
public class DumpCode extends ByteCode {

    private String Status;

    @Override
    public void init(ArrayList<String> args) {
        Status = args.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        try {
            if (Status.equals("ON")) {
                vm.setDumpFlagOn();
            } else if (Status.equals("OFF")) {
                vm.setDumpFlagOff();
            } else {
                throw new IOException();
            }
        } catch (Exception e) {
            System.out.println("**** " + e);
        }
    }
}
