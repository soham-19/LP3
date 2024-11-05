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
