import java.util.Scanner;

class Item {
    int id, wt, val;
    public Item(int i, int w, int v) {
        id = i; wt = w; val = v;
    }
}
public class DP {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.print("Enter number of items: ");
        int n = sc.nextInt();

        Item[] items = new Item[n];

        for(int i=1; i<=n; i++) {
            System.out.println("Item " + (i) + ": ");
            System.out.print("\tWeight: ");
            int w = sc.nextInt();
            System.out.print("\tValue: ");
            int v = sc.nextInt();
            items[i-1] = new Item(i, w, v);
        }

        System.out.print("Enter capacity of knapsack: ");
        int cap = sc.nextInt();

        dp(items, cap);
    }

    private static void dp(Item[] items, int cap) {
        int n = items.length;
        int[][] dp = new int[n+1][cap+1];

        for(int i=1; i<=n; i++) {
            int j = i-1;
            for(int w=1; w<=cap; w++) {
                if(items[j].wt <= w) {
                    dp[i][w] = Math.max(dp[j][w], items[j].val + dp[j][w-items[j].wt]);
                } else {
                    dp[i][w] = dp[j][w];
                }
            }
        }

        boolean[] taken = new boolean[n];
        int w = cap;
        for(int i=1; i<=n; i++) {
            if(dp[i][w] != dp[i-1][w]) {
                taken[i-1] = true;
                w -= items[i-1].wt;
            }
        }

        System.out.println("Item\tWeight\tValue");
        for(int i=0; i<n; i++) {
            if(taken[i]) {
                Item it = items[i];
                System.out.println(it.id + "\t" + it.wt + "\t" + it.val);
            }
        }

        System.out.println("DP["+ n + "][" + cap + "] = " +dp[n][cap]);
    }
}

/*
dp() Function:

Initializes a DP table dp where:
dp[i][w] represents the maximum value achievable with the first i items and weight limit w.
DP Table Construction:
Iterates through each item and each weight limit (1 to cap).
Condition Check:
If the item’s weight wt is less than or equal to the current weight limit w, two cases are considered:
Exclude the item (dp[i-1][w]).
Include the item (val + dp[i-1][w - wt]).
The maximum of these two values is stored in dp[i][w].
Item Selection (Backtracking):
Traverses the DP table from dp[n][cap] back to dp[0][0] to determine which items contribute to the maximum value.
Marks items as "taken" if their inclusion in the solution set is indicated by the DP table.
Output:
Prints the items included in the optimal solution, displaying each item’s ID, weight, and value.
Displays the maximum achievable value (dp[n][cap]).
Complexity Analysis
Time Complexity: O(n×cap)
Two nested loops, one iterating over items (n) and the other over capacities (cap).
Space Complexity: O(n×cap)
DP table storage requirements.
Real-World Application
Examples: Resource allocation, budget management, selecting projects to maximize profit within constraints.

Why Greedy Fails
Greedy cannot "look ahead" to consider that skipping a high-ratio item might allow for a better combination later.
It fails to account for the interaction between item weights and the total capacity, which is essential in 0/1 Knapsack.
Key Points
0/1 Constraint: Items must be taken as a whole, not in fractions.
Optimal Substructure: The solution depends on combining items in a way that maximizes the total value, not just maximizing each step locally.
Global Optimum Ignored: Greedy makes decisions that are optimal for the moment but may not yield the highest total value.
*/
