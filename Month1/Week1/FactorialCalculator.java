import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class FactorialCalculator {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter a non-negative integer: ");
        int num = Integer.parseInt(reader.readLine());

        if (num < 0) {
            System.out.println("Factorial is not defined for negative numbers.");
            return;
        }

        long factorial = 1;
        for (long i = 2; i <= num; i++) {
            factorial = factorial * i;
        }

        System.out.println("Factorial of " + num + " is: " + factorial);
    }
}
