import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class SafeDivision {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter numerator: ");
            int num = Integer.parseInt(reader.readLine());

            System.out.print("Enter denominator: ");
            int denom = Integer.parseInt(reader.readLine());

            try {
                int result = num / denom;
                System.out.println("Result: " + result);
            } catch (ArithmeticException e) {
                System.out.println("Error: Division by zero is not allowed.");
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    }
}
