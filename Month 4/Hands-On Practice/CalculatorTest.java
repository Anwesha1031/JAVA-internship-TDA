class Calculator {
    int add(int a, int b){ return a + b; }
    int divide(int a, int b){ return a / b; }
}

public class CalculatorTest {
    public static void main(String[] args) {
        Calculator c = new Calculator();

        // Unit Tests
        assert c.add(2,3) == 5 : "Addition failed!";
        assert c.divide(6,3) == 2 : "Division failed!";

        // Integration Test (simulate usage)
        int total = c.add(c.divide(8,2), 4); // (4+4)=8
        assert total == 8 : "Integration test failed!";

        System.out.println("✅ All tests passed successfully!");
    }
}
