java
// File: Main.java

import java.util.regex.Pattern;

public class Main {

    private static final Pattern INTEGER_REGEX = Pattern.compile("-?\\d+");

    public static void main(String[] args) {

        System.out.println("Hello, world!");

        int[] arr = {1, 2, 3, 4, 5};

        if (args.length > 0 && INTEGER_REGEX.matcher(args[0]).matches()) {
            
            int index = Integer.parseInt(args[0]);
            
            if (index >= 0 && index < arr.length) {
                System.out.println("Value at index " + index + ": " + arr[index]); 
            } else {
                System.out.println("Index out of bounds");
            }
            
        } else {
            System.out.println("Invalid input");
        }

    }

}