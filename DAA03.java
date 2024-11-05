import java.util.*;

class Item {
    int id;
    int weight;
    int value;
    double ratio;

    public Item(int id, int weight, int value) {
        this.id = id;
        this.weight = weight;
        this.value = value;
        this.ratio = (double) value / weight; // Calculate ratio once in the constructor
    }
}

public class FractionalKnapsack {

    public static double getMaxValue(Item[] items, int capacity) {

        Arrays.sort(items, (i1, i2) -> Double.compare(i2.ratio, i1.ratio)); // Sort items by value-to-weight ratio

        double totalValue = 0.0;
        int initialCapacity = capacity;

        for (Item item : items) {
            if (capacity == 0)
                break;

            if (item.weight <= capacity) { // If item can be added fully, add its whole value
                capacity -= item.weight;
                totalValue += item.value;
            } else { // Add fraction of the item's value corresponding to available capacity
                totalValue += item.value * ((double) capacity / item.weight);
                capacity = 0;
            }
        }

        System.out.println(
                "\nMaximum value achievable in Knapsack with capacity " + initialCapacity + " = " + totalValue);
        return totalValue;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of items: ");
        int n = scanner.nextInt();
        Item[] items = new Item[n];

        // User input for weights and values
        for (int i = 0; i < n; i++) {
            System.out.print("Enter weight for item " + (i + 1) + ": ");
            int weight = scanner.nextInt();
            System.out.print("Enter value for item " + (i + 1) + ": ");
            int value = scanner.nextInt();
            items[i] = new Item(i + 1, weight, value);
        }

        System.out.print("\nEnter capacity of the knapsack: ");
        int capacity = scanner.nextInt();

        // Calculate and display maximum value for the given capacity
        getMaxValue(items, capacity);
        scanner.close();
    }
}

/*
 * THEORY
 * Greedy Strategy Explanation
 * Objective: Maximize the total value of items within a fixed capacity by
 * prioritizing items with the highest value-to-weight ratio.
 * Sorting: Ensures that items with higher ratios are considered first for
 * maximum value gain per unit weight.
 * Fractional Items: Allows taking a fraction of an item if it exceeds the
 * remaining capacity, which is crucial for maximizing value when the exact
 * weight capacity is not achievable with whole items.
 * Time Complexity
 * Sorting: 𝑂(𝑛log⁡𝑛)
 * O(nlogn)
 * Iterating Over Items: O(n)
 * Total Complexity: O(nlogn)
 * Space Complexity: O(n) for storing item details.
 * Real-World Application
 * Examples: Transport and logistics (maximizing profit with limited cargo
 * capacity), investment portfolio selection (allocating funds to maximize
 * returns with constraints).
 */
