package fibonacci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A program that finds the Fibonacci number at Nth (given N as input) using
 * both recursive and iterative functions. It compares the number of function
 * calls between recursive and iterative approach.
 *
 * @author Terry Wong
 */
public class Fibonacci_Compare {

    //A variable for keeping track of the number of function calls
    private static int nfc_iter = 0, nfc_recur = 0;

    //The fibonacci iterative function
    public static long fib_iter(int n) {
        int i;
        int[] f = new int[n + 1];

        nfc_iter++;
        f[0] = 0;
        if (n > 0) {
            f[1] = 1;
            for (i = 2; i <= n; i++) {
                f[i] = f[i - 1] + f[i - 2];
            }
        }
        return f[n];
    }

    //The fibonacci recursive function
    public static long fib_recur(int n) {
        nfc_recur++;
        if (n <= 1) {
            return n;
        } else {
            return fib_recur(n - 1) + fib_recur(n - 2);
        }
    }

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter a number for the Nth term in the Fibonacci Sequence");
        System.out.print(">> ");
        try {
            String UserInput = br.readLine();
            int n = Integer.parseInt(UserInput);
            long result = fib_iter(n);
            System.out.println("For the iteractive approach:");
            System.out.println("n: " + n + "\tfib_iter(" + n + "): " + result);
            System.out.println("# of function calls: " + nfc_iter + "\n\n");
            result = fib_recur(n);
            System.out.println("For the recursive approach:");
            System.out.println("n: " + n + "\tfib_recur(" + n + "): " + result);
            System.out.println("# of function calls: " + nfc_recur);

        } catch (IOException e) {
            System.out.println("****Incorrect Command**** " + e);
        }
    }
}
