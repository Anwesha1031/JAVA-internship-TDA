import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ConsoleCalculator {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the first number: ");
        double num1 = Double.parseDouble(reader.readLine());

        System.out.print("Enter an operator (+, -, *, /): ");
        char operator = reader.readLine().charAt(0);

        System.out.print("Enter the second number: ");
        double num2 = Double.parseDouble(reader.readLine());

        double result;

        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0)
                    result = num1 / num2;
                else {
                    System.out.println("Error: Division by zero is not allowed.");
                    return;
                }
                break;
            default:
                System.out.println("Invalid operator.");
                return;
        }

        System.out.println("Result: " + result);
    }
}
