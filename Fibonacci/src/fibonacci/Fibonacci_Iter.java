package fibonacci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//The fibonacci iterative function
public class Fibonacci_Iter {

    private static int nbo = 0;

    public static long fib(int n) {
        int i;
        int[] f = new int[n + 1];

        f[0] = 0;
        if (n > 0) {
            f[1] = 1;
            for (i = 2; i <= n; i++) {
                f[i] = f[i - 1] + f[i - 2];
                nbo++;
            }
        }
        return f[n];
    }

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter a number in multiples of 5 for");
        System.out.println("the nth term in the Fibonacci Sequence that we are calculating up to:");
        System.out.print(">> ");
        try {
            String UserInput = br.readLine();
            int n = Integer.parseInt(UserInput);
            if (n % 5 != 0) {
                throw new IOException();
            }
            for (int i = 0; i <= n; i += 5) {
                long startTime = System.nanoTime();
                long result = fib(i);
                long endTime = System.nanoTime();
                System.out.print("n: " + i + "\tfib(" + i + "): " + result);
                System.out.println("\t\t#Basic Op: " + nbo + "\tRunning Time:" + (endTime - startTime) / 1000000000 + " s");
            }
        } catch (IOException e) {
            System.out.println("****Incorrect Command**** " + e);
        }
    }
}
