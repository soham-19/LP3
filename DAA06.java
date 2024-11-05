import java.util.Scanner;
import java.util.Random;

public class QuickSortAnalysis {

    // Deterministic variant of Quick Sort
    public static void quickSortDeterministic(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortDeterministic(arr, low, pi - 1);
            quickSortDeterministic(arr, pi + 1, high);
        }
    }

    // Randomized variant of Quick Sort
    public static void quickSortRandomized(int[] arr, int low, int high) {
        if (low < high) {
            int pi = randomizedPartition(arr, low, high);
            quickSortRandomized(arr, low, pi - 1);
            quickSortRandomized(arr, pi + 1, high);
        }
    }

    // Partition function for deterministic Quick Sort
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // last element as pivot
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    // Randomized partition function
    private static int randomizedPartition(int[] arr, int low, int high) {
        Random rand = new Random();
        int randomIndex = low + rand.nextInt(high - low);
        int temp = arr[randomIndex];
        arr[randomIndex] = arr[high];
        arr[high] = temp;
        return partition(arr, low, high);
    }

    // Function to print the array
    public static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of elements: ");
        int n = scanner.nextInt();
        int[] arr = new int[n];

        System.out.println("Enter the elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        System.out.println("Select Quick Sort Variant:");
        System.out.println("1. Deterministic");
        System.out.println("2. Randomized");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Sorting using Deterministic Quick Sort...");
                quickSortDeterministic(arr, 0, arr.length - 1);
                break;
            case 2:
                System.out.println("Sorting using Randomized Quick Sort...");
                quickSortRandomized(arr, 0, arr.length - 1);
                break;
            default:
                System.out.println("Invalid choice! Exiting...");
                System.exit(0);
        }

        System.out.println("Sorted array:");
        printArray(arr);
        scanner.close();
    }
}