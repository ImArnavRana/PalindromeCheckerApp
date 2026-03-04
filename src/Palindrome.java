import java.util.Scanner;
import java.util.Stack;

public class Palindrome{

    public static boolean reverseMethod(String input) {
        String reversed = "";
        for (int i = input.length() - 1; i >= 0; i--) {
            reversed += input.charAt(i);
        }
        return input.equals(reversed);
    }

    public static boolean twoPointerMethod(String input) {
        int start = 0;
        int end = input.length() - 1;

        while (start < end) {
            if (input.charAt(start) != input.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public static boolean stackMethod(String input) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            stack.push(input.charAt(i));
        }

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != stack.pop()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Palindrome Performance Comparison");
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        long startTime, endTime;

        startTime = System.nanoTime();
        boolean result1 = reverseMethod(input);
        endTime = System.nanoTime();
        long reverseTime = endTime - startTime;

        startTime = System.nanoTime();
        boolean result2 = twoPointerMethod(input);
        endTime = System.nanoTime();
        long twoPointerTime = endTime - startTime;

        startTime = System.nanoTime();
        boolean result3 = stackMethod(input);
        endTime = System.nanoTime();
        long stackTime = endTime - startTime;

        System.out.println("\nResults:");
        System.out.println("Reverse Method: " + result1 + " | Time: " + reverseTime + " ns");
        System.out.println("Two Pointer Method: " + result2 + " | Time: " + twoPointerTime + " ns");
        System.out.println("Stack Method: " + result3 + " | Time: " + stackTime + " ns");

        scanner.close();
    }
}