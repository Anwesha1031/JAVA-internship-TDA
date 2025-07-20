import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class PalindromeChecker {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter a word: ");
        String word = reader.readLine();
        String reversed = new StringBuilder(word).reverse().toString();

        if (word.equalsIgnoreCase(reversed)) {
            System.out.println("The word is a palindrome.");
        } else {
            System.out.println("The word is NOT a palindrome.");
        }
    }
}
