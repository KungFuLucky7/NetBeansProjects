package fibonacci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//The fibonacci recursive function
public class Fibonacci_Recur {

    private static long nbo = 0;

    public static long fib(int n) {
        nbo++;
        if (n <= 1) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
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
                System.out.println("\t\t#Basic Op: " + nbo + "\tRunning Time: " + (endTime - startTime) / 1000000000 + " s");
            }
        } catch (IOException e) {
            System.out.println("****Incorrect Command**** " + e);
        }
    }
}
