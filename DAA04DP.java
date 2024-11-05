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
