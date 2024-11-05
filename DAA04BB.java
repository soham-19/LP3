import java.util.*;

// Class to represent items in the knapsack
class KnapsackItem {
    int id;         // Unique identifier for the item
    int weight;     // Weight of the item
    int value;      // Value of the item
    double ratio;   // Value-to-weight ratio

    KnapsackItem(int id, int weight, int value) {
        this.id = id;
        this.weight = weight;
        this.value = value;
        this.ratio = (double) value / weight;
    }
}

// Node class for branch and bound strategy
class Node {
    int level, profit, weight;
    double bound;
    boolean isTaken; // To track if the item is taken

    Node(int level, int profit, int weight, double bound, boolean isTaken) {
        this.level = level;
        this.profit = profit;
        this.weight = weight;
        this.bound = bound;
        this.isTaken = isTaken;
    }
}

public class KnapsackSolver {

    // Method to calculate upper bound for branch and bound node
    private static double calculateBound(Node u, int n, int capacity, KnapsackItem[] items) {
        if (u.weight >= capacity) return 0;

        double bound = u.profit;
        int j = u.level + 1;
        int totalWeight = u.weight;

        // Add full items while weight allows
        while ((j < n) && (totalWeight + items[j].weight <= capacity)) {
            totalWeight += items[j].weight;
            bound += items[j].value;
            j++;
        }

        // Take the fraction of the next item if possible
        if (j < n) {
            bound += (capacity - totalWeight) * items[j].ratio;
        }
        return bound;
    }

    // Branch and Bound method for 0-1 Knapsack
    public static int knapsackBranchAndBound(KnapsackItem[] items, int capacity) {
        int n = items.length;

        // Sort items by value-to-weight ratio
        Arrays.sort(items, (i1, i2) -> Double.compare(i2.ratio, i1.ratio));

        // Initialize priority queue for nodes
        PriorityQueue<Node> queue = new PriorityQueue<>((n1, n2) -> Double.compare(n2.bound, n1.bound));
        Node u = new Node(-1, 0, 0, 0.0, false);
        u.bound = calculateBound(u, n, capacity, items);
        queue.add(u);

        int maxProfit = 0;
        boolean[] taken = new boolean[n]; // To track included items

        // Process nodes
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.level == -1) node.level = 0;
            if (node.level == n - 1) continue;

            // Include next item
            Node v = new Node(node.level + 1, node.profit + items[node.level + 1].value,
                              node.weight + items[node.level + 1].weight, 0.0, true);

            // Update max profit and mark item as taken
            if (v.weight <= capacity && v.profit > maxProfit) {
                maxProfit = v.profit;
                taken[node.level + 1] = true; // Mark this item as taken
            }

            v.bound = calculateBound(v, n, capacity, items);
            if (v.bound > maxProfit) {
                queue.add(v);
            }

            // Exclude next item
            v = new Node(node.level + 1, node.profit, node.weight, 0.0, false);
            v.bound = calculateBound(v, n, capacity, items);
            if (v.bound > maxProfit) {
                queue.add(v);
            }
        }

        // Print the results
        System.out.println("\nResultant Profit: " + maxProfit);
        System.out.print("Resultant Solution: ");
        for (boolean item : taken) {
            System.out.print((item ? 1 : 0) + " ");
        }
        System.out.println();

        return maxProfit;
    }

    // Dynamic Programming method for 0-1 Knapsack
    public static int knapsackDP(KnapsackItem[] items, int capacity) {
        int n = items.length;
        int[][] dp = new int[n + 1][capacity + 1];

        // Build the DP table
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                if (items[i - 1].weight <= w) {
                    dp[i][w] = Math.max(items[i - 1].value + dp[i - 1][w - items[i - 1].weight], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Backtrack to find which items were taken
        boolean[] taken = new boolean[n];
        int w = capacity;
        for (int i = n; i > 0 && w >= 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) { // Item i was included
                taken[i - 1] = true;
                w -= items[i - 1].weight;
            }
        }

        // Print the results
        System.out.println("\nResultant Profit: " + dp[n][capacity]);
        System.out.print("Resultant Solution: ");
        for (boolean item : taken) {
            System.out.print((item ? 1 : 0) + " ");
        }
        System.out.println();

        return dp[n][capacity];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter number of items (or 0 to exit): ");
            int n = scanner.nextInt();
            if (n == 0) break; // Exit condition

            KnapsackItem[] items = new KnapsackItem[n];

            System.out.println("Enter weights and values of items:");
            for (int i = 0; i < n; i++) {
                System.out.print("Item " + (i + 1) + " weight: ");
                int weight = scanner.nextInt();
                System.out.print("Item " + (i + 1) + " value: ");
                int value = scanner.nextInt();
                items[i] = new KnapsackItem(i + 1, weight, value);
            }

            System.out.print("Enter capacity of the knapsack: ");
            int capacity = scanner.nextInt();

            System.out.println("\n--- Input Items ---");
            System.out.println("ID\tWeight\tValue\tRatio");
            for (KnapsackItem item : items) {
                System.out.printf("%d\t%d\t%d\t%.2f\n", item.id, item.weight, item.value, item.ratio);
            }

            System.out.println("\nChoose a method to solve the 0-1 Knapsack problem:");
            System.out.println("1. Branch and Bound");
            System.out.println("2. Dynamic Programming");
            System.out.print("Enter choice (1 or 2): ");
            int choice = scanner.nextInt();

            int maxValue;
            switch (choice) {
                case 1:
                    maxValue = knapsackBranchAndBound(items, capacity);
                    break;
                case 2:
                    maxValue = knapsackDP(items, capacity);
                    break;
                default:
                    System.out.println("Invalid choice. Please choose 1 or 2.");
            }
        }

        scanner.close();
        System.out.println("Exiting program.");
    }
}

/*
1. Overview of Branch and Bound (B&B)
Purpose: B&B is designed to efficiently handle combinatorial optimization problems by discarding large portions of the solution space that are unlikely to lead to an optimal solution.
Knapsack Context: In the 0/1 Knapsack Problem, we aim to maximize the value in the knapsack without exceeding its weight capacity. B&B selectively explores subsets of items, only diving into those with a high chance of yielding a maximum profit.
2. Key Concepts in B&B for 0/1 Knapsack
Node Representation: Each node in the B&B tree represents a choice (i.e., either including or excluding a specific item).
Levels: Each level in the B&B tree corresponds to an item. At each node, you decide either to:
Include the item (adding its weight and value to the current knapsack configuration).
Exclude the item (skipping to the next item without adding anything).
Bounds: For each node, B&B calculates an upper bound of the maximum possible profit achievable from that node. This bound helps in pruning the search space.
3. Bound Calculation
The bound serves as an estimate of the maximum profit achievable from a particular node onward. It is calculated by:

Adding full items from the current node level onward until the weight limit is reached.
Adding a fraction of the next item (if needed) to partially fill the remaining capacity.
This approach gives a theoretical upper limit on the profit, allowing us to avoid nodes that can't potentially improve the current best solution.

4. Steps in B&B for 0/1 Knapsack
Sort Items by Value-to-Weight Ratio: This sorting helps prioritize items with higher potential for maximizing profit.
Initialize Priority Queue:
The priority queue is used to store nodes based on their bound in descending order, ensuring the node with the highest potential is processed first.
Process Nodes:
Include Current Item: Create a child node representing the inclusion of the current item, calculate its profit, weight, and bound.
Exclude Current Item: Create another child node representing the exclusion of the current item.
Bound Check: If a node’s bound is less than the current maximum profit, it is discarded (pruned), as it cannot lead to an optimal solution.
Update Maximum Profit:
As nodes are processed, update the maximum profit if a new higher profit is found at any feasible solution (i.e., a node that does not exceed capacity).
Continue Until Queue is Empty: The algorithm stops when all promising nodes have been processed.
5. Advantages of B&B in Knapsack
Efficient Pruning: B&B prunes large parts of the search space using the bound, leading to potentially significant reductions in computation.
Optimal Solution: Given enough memory and computation time, B&B is guaranteed to find the optimal solution by exploring only feasible configurations with the highest potential.
6. Limitations of B&B in Knapsack
Memory Usage: The B&B tree can grow large, especially for instances with many items, as each level in the tree represents an item decision.
Bound Calculation Complexity: Calculating the bound for each node adds overhead, which may impact performance if not implemented efficiently.

Branch and Bound (B&B) Analysis for 0/1 Knapsack
Time Complexity:

Worst-case: Exponential, O(2^n), as it may explore all subsets if pruning is ineffective.
Average-case: Often much better due to pruning, especially with items sorted by value-to-weight ratio, reducing search space significantly.
Space Complexity:

Depends on the branching, generally O(2^n) in the worst case due to potential storage of all nodes.
Priority queues used for storing nodes add to space usage but aid in efficient pruning.
*/
