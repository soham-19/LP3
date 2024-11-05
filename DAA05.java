import java.util.Scanner;

public class NQueensProblem {
    static Scanner sc = new Scanner(System.in);

    static int fixedRow = 0;
    static int fixedCol = 0;
    static int count = 0;

    public static void main(String[] args) {
        System.out.print("Enter value of n: ");
        int n = sc.nextInt();

        char[][] board = initializeBoard(n);

        System.out.print("Enter row where you are placing the 1st queen: (0 to " + (n - 1) + "): ");
        fixedRow = sc.nextInt();
        System.out.print("Enter column where you are placing the 1st queen: (0 to " + (n - 1) + "): ");
        fixedCol = sc.nextInt();

        boolean[] cols = new boolean[n];
        boolean[] mainD = new boolean[2 * n - 1];
        boolean[] antiD = new boolean[2 * n - 1];

        place1stQueen(board, cols, mainD, antiD);

        nQueens(board, 0, cols, mainD, antiD);

        System.out.println("Solutions: " + count);
    }

    private static char[][] initializeBoard(int n) {
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = 'x';
            }
        }
        return board;
    }

    private static void place1stQueen(char[][] board, boolean[] cols, boolean[] mainD, boolean[] antiD) {
        cols[fixedCol] = true;
        mainD[fixedRow - fixedCol + board.length - 1] = true;
        antiD[fixedRow + fixedCol] = true;
        board[fixedRow][fixedCol] = 'Q';
    }

    private static void printBoard(char[][] board) {
        System.out.println("-------------Solution--------------");
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }

    private static void nQueens(char[][] board, int row, boolean[] cols, boolean[] mainD, boolean[] antiD) {
        int n = board.length;

        if (row == n) {
            printBoard(board);
            count++;
            return;
        }

        if (row == fixedRow) {
            nQueens(board, row + 1, cols, mainD, antiD);
            return;
        }

        for (int col = 0; col < n; col++) {
            if (cols[col] ||
                    mainD[row - col + n - 1] ||
                    antiD[row + col]) {
                continue;
            }

            board[row][col] = 'Q';
            cols[col] = mainD[row - col + n - 1] = antiD[row + col] = true;

            nQueens(board, row + 1, cols, mainD, antiD);

            board[row][col] = 'x'; // backtracking
            cols[col] = mainD[row - col + n - 1] = antiD[row + col] = false; //backtracking
        }
    }
}

/*
        THEORY

### N-Queens Problem: Concepts and Approach

1. **N-Queens Problem**:
   - **What**: Place \( n \) queens on an \( n \times n \) chessboard so that no two queens threaten each other.
   - **Why**: A classic problem to explore backtracking, recursion, and constraint satisfaction in algorithm design.

2. **Fixed Position for the First Queen**:
   - **Purpose**: Allows the user to place the first queen manually, reducing the number of choices for the rest of the queens.
   - **Impact**: Simplifies the solution by reducing the solution space as one queen’s position is fixed.

3. **Backtracking Strategy**:
   - **What**: A recursive approach that places queens one row at a time and backtracks if a conflict occurs.
   - **Why**: Efficient for constraint satisfaction problems where solutions can be incrementally constructed and pruned.
   - **How**: Each position is checked for validity, and if a queen placement leads to a conflict, the program backtracks, resetting to the previous state.

4. **Constraint Arrays**:
   - **Columns (`cols`)**: Keeps track of which columns are already occupied by a queen.
   - **Main Diagonals (`mainD`)**: Tracks main diagonals (from top-left to bottom-right) for queen conflicts.
   - **Anti-Diagonals (`antiD`)**: Tracks anti-diagonals (from top-right to bottom-left) for queen conflicts.
   - **Purpose**: Prevents placing queens in positions where they can threaten each other, ensuring valid placements.

### Steps of the Algorithm

1. **Initialize the Board**:
   - Create an empty board filled with `'x'` to represent unoccupied cells.

2. **Place the First Queen**:
   - Manually place the first queen based on user input and update the constraint arrays for that position.

3. **Recursive Placement (`nQueens` function)**:
   - For each row, attempt to place a queen in each column.
   - Check if the column, main diagonal, and anti-diagonal are available.
   - If valid, place the queen, update constraints, and recursively call the function for the next row.
   - If a conflict occurs in future placements, backtrack by removing the queen and resetting constraints.

4. **Count Solutions**:
   - Increment the solution count each time a complete valid arrangement is found.

5. **Display Solutions**:
   - Each valid board configuration is printed when found, showing a possible arrangement.

### Algorithm Analysis

1. **Time Complexity**:
   - **Worst-Case**: \( O(n!) \), as each row placement potentially requires checking every column.
   - **Optimization**: Reduces complexity by backtracking early if conflicts are found.
   
2. **Space Complexity**:
   - **Board**: \( O(n^2) \), for storing the board configuration.
   - **Constraint Arrays**: \( O(n) \) for `cols`, \( O(2n-1) \) for diagonals.
   - **Overall**: \( O(n^2) \), mainly due to recursive stack usage and board storage.

### Real-World Applications

- **Resource Allocation**: Solving allocation problems where resources must be placed without conflicts.
- **Scheduling**: Useful in job scheduling where constraints prevent certain jobs from being placed together.
- **Puzzle Solving**: This approach applies to other backtracking problems like Sudoku or crosswords.
       
 */
