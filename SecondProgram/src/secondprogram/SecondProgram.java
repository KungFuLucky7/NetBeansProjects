package secondprogram;

public class SecondProgram {

    public static void main(String args[]) {
        if (args.length > 0) {
            System.out.println("Hello");
            for (int i = 0; i < args.length; i++) {
                System.out.println(i + 1 + ". " + args[i]);
            }
            System.out.println(System.getProperty("java.home"));
        } else {
            System.out.println("Hello everybody!");
        }

    }
}
