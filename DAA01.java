/*
Write a program non-recursive and recursive program to calculate Fibonacci numbers and 
analyze their time and space complexity.
*/

import java.util.Scanner;

public class Fib {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int n = 0;

        while (true) {
            System.out.println("Fibonacci Program");
            System.out.println("1 - Iterative Algorithm");
            System.out.println("2 - Naive Recursive Algorithm");
            System.out.println("3 - DP: Tabulation");
            System.out.println("4 - DP: Memoization");
            System.out.println("0 - Exit");

            System.out.print(" Enter your choice: ");
            int choice = sc.nextInt();

            if (choice != 0) {
                System.out.print("Enter value of n : ");
                n = sc.nextInt();
            }

            switch (choice) {
                case 0:
                    System.exit(0);

                case 1:
                    iterative(n);
                    break;

                case 2:
                    recursive(n);
                    break;

                case 3:
                    tabulation(n);
                    break;

                case 4:
                    memoization(n);
                    break;

                default:
                    break;
            }
        }
    }

    public static void iterative(int n) {
        int a = 0, b = 1;
        for (int i = 1; i <= n; i++) {
            System.out.print(a + " ");
            int fib = a + b;
            a = b;
            b = fib;
        }
        System.out.println("\n");
    }

    public static void recursive(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(rec(i) + " ");
        }
        System.out.println("\n");
    }

    public static int rec(int i) {
        return i <= 1 ? i : rec(i - 1) + rec(i - 2);
    }

    public static void tabulation(int n) {
        int[] fib = new int[n];
        fib[0] = 0;
        if (n > 1) {
            fib[1] = 1;
            for (int i = 2; i < n; i++) {
                fib[i] = fib[i - 1] + fib[i - 2];
            }
            for (int i = 0; i < n; i++) {
                System.out.print(fib[i] + " ");
            }
            System.out.println("\n");
        }
    }

    public static Integer[] memo;

    public static void memoization(int n) {
        memo = new Integer[n + 1];
        for (int i = 0; i < n; i++) {
            System.out.print(memoFib(i) + " ");
        }
        System.out.println("\n");
    }

    public static int memoFib(int n) {
        // Check if the result for this Fibonacci number has already been computed
        if (memo[n] != null) {
            return memo[n];
        }

        // Base cases
        if (n <= 1) {
            return n;
        }

        // Store the computed result in memo array to avoid re-computation
        memo[n] = memoFib(n - 1) + memoFib(n - 2);
        return memo[n];
    }

}

/*
        THEORY
Here's a concise explanation of the four approaches used to calculate Fibonacci numbers, along with time and space complexity analysis:

### 1. Iterative Approach
- **Code Explanation**:
  - Initializes two variables `a` and `b` to hold previous Fibonacci numbers.
  - Iteratively updates these values to calculate up to the \( n \)-th Fibonacci number.
- **Time Complexity**: \( O(n) \) — One loop that runs \( n \) times.
- **Space Complexity**: \( O(1) \) — Constant space, as it only uses a few variables.

### 2. Naive Recursive Approach
- **Code Explanation**:
  - Direct recursive function that calculates Fibonacci number \( F(n) = F(n-1) + F(n-2) \).
  - Redundant calculations lead to exponential time complexity.
- **Time Complexity**: \( O(2^n) \) — Recomputes values multiple times due to overlapping subproblems.
- **Space Complexity**: \( O(n) \) — Stack space for recursive calls.

### 3. Dynamic Programming: Tabulation (Bottom-Up Approach)
- **Code Explanation**:
  - Uses an array `fib` to store intermediate results of Fibonacci numbers in a bottom-up manner.
  - Avoids redundant calculations by using previously calculated results directly.
- **Time Complexity**: \( O(n) \) — Single loop through \( n \) values.
- **Space Complexity**: \( O(n) \) — Uses an array of size \( n \) to store Fibonacci numbers.

### 4. Dynamic Programming: Memoization (Top-Down Approach)
- **Code Explanation**:
  - Uses recursion with a cache (`memo`) to store previously computed Fibonacci numbers.
  - Each Fibonacci number is calculated once and reused, optimizing the recursive approach.
- **Time Complexity**: \( O(n) \) — Each Fibonacci number is calculated only once.
- **Space Complexity**: \( O(n) \) — Uses an array of size \( n \) for memoization.

### Summary of Usage
- **Iterative Approach**: Best for simple cases where minimal memory is required.
- **Naive Recursive**: Inefficient but demonstrates basic recursion.
- **Tabulation**: Efficient for cases requiring all Fibonacci values up to \( n \).
- **Memoization**: Efficient for recursive calculations, especially in large computations with overlapping subproblems.        
 */
