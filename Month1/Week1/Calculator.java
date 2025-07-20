import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Calculator {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter first number: ");
        double num1 = Double.parseDouble(reader.readLine());

        System.out.print("Enter operator (+, -, *, /): ");
        char operator = reader.readLine().charAt(0);

        System.out.print("Enter second number: ");
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
                    System.out.println("Error: Division by zero.");
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
