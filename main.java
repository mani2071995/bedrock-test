// File: Main.java
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        int[] arr = {1, 2, 3, 4, 5};
        int index = Integer.parseInt(args[0]); // Vulnerability: No input validation
        System.out.println("Value at index " + index + ": " + arr[index]); // Vulnerability: Array index out of bounds
    }
}
