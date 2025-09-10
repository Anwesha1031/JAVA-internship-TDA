import java.io.*;
import java.util.*;
import java.util.stream.*;

public class StreamFilter {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter numbers separated by space: ");
        String[] input = br.readLine().split(" ");

        // Convert to List<Integer>
        List<Integer> numbers = Arrays.stream(input)
                                      .map(Integer::parseInt)
                                      .collect(Collectors.toList());

        // Filter even numbers using lambda + streams
        List<Integer> evenNumbers = numbers.stream()
                                           .filter(n -> n % 2 == 0)
                                           .collect(Collectors.toList());

        System.out.println("Even Numbers: " + evenNumbers);

        // Double each number using map()
        List<Integer> doubled = numbers.stream()
                                       .map(n -> n * 2)
                                       .collect(Collectors.toList());

        System.out.println("Doubled Numbers: " + doubled);
    }
}
