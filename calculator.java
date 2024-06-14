java
// File: Calculator.java
public class Calculator {
    public int add(int a, int b) {
        return a + b; 
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisor cannot be 0");
        }
        return a / b;
    }
}
