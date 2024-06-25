java
// File: Main.java
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        int[] arr = {1, 2, 3, 4, 5};
        
        if(args.length < 1) {
            System.out.println("Missing index argument");
            return;
        }
        
        int index;
        try {
            index = Integer.parseInt(args[0]); 
        } catch (NumberFormatException e) {
            System.out.println("Index must be an integer");
            return;
        }
        
        if(index < 0 || index >= arr.length) {
            System.out.println("Index out of bounds");
            return;
        }
        
        System.out.println("Value at index " + index + ": " + arr[index]);
    }
}
