java
// File: Main.java
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        
        if (args == null || args.length == 0) {
            System.out.println("No input provided");
            return;
        }
        
        try {
            int index = Integer.parseInt(args[0]);
            
            if (index < 0 || index >= arr.length) {
                throw new IndexOutOfBoundsException(); 
            }
            
            int[] arr = {1, 2, 3, 4, 5}; 
            System.out.println("Value at index " + index + ": " + arr[index]);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds");
        }
    }
}