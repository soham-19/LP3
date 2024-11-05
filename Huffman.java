// 2.  Write a program to implement Huffman Encoding using a greedy strategy. 

import java.util.*;

class Node {
    char ch;
    int freq;
    Node left, right;

    Node(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
        this.left = this.right = null;
    }
}

class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2) {
        return n1.freq - n2.freq;
    }
}

public class Huffman {

    public static void generateCodes(Node root, String code, HashMap<Character, String> codes) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            codes.put(root.ch, code);
        }

        generateCodes(root.left, code + "0", codes);
        generateCodes(root.right, code + "1", codes);
    }

    public static HashMap<Character, String> buildHuffmanTree(HashMap<Character, Integer> charFreq) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());

        for (Character ch : charFreq.keySet()) {
            pq.add(new Node(ch, charFreq.get(ch)));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();

            Node merged = new Node('#', left.freq + right.freq);
            merged.left = left;
            merged.right = right;

            pq.add(merged);
        }

        Node root = pq.poll();

        HashMap<Character, String> codes = new HashMap<>();
        generateCodes(root, "", codes);
        return codes;
    }

    public static void printCodes(HashMap<Character, String> codes) {
        for(char ch : codes.keySet()) {
            System.out.println(ch + ": " + codes.get(ch));
        }
    }

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Input the characters and frequencies
        HashMap<Character, Integer> charFreq = new HashMap<>();

        System.out.print("Enter the number of characters: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter character and its frequency: ");
            char ch = sc.next().charAt(0);
            int freq = sc.nextInt();
            charFreq.put(ch, freq);
        }

        // Build the Huffman Tree and generate the codes
        HashMap<Character, String> codes = buildHuffmanTree(charFreq);
        // Print the Codes for each character
        printCodes(codes);
    }
}

/*
    THEORY

### Huffman Encoding: Theory and Concepts

1. **Huffman Encoding**:
   - **What**: A compression algorithm that reduces the number of bits needed to represent a set of characters based on their frequencies.
   - **Why**: Efficient for data compression in text and multimedia files, as characters with higher frequency get shorter codes.
   - **How**: Uses a binary tree structure where each character is assigned a binary code, and no code is a prefix of another (prefix-free coding).

2. **Greedy Approach**:
   - **What**: Huffman Encoding is a greedy algorithm, which means it makes locally optimal choices to build the optimal solution (minimum-cost tree).
   - **Why**: Greedy strategy ensures minimal total bits by combining the lowest-frequency nodes first, progressively building up to the root.

3. **Tree Structure**:
   - **Nodes**: Each leaf node represents a character, and each path from root to leaf represents the binary code of that character.
   - **Internal Nodes**: These are merged nodes without specific characters, used only to organize the tree structure and encode paths.

### Steps in Huffman Encoding (in the Code)

1. **Build Frequency Map**:
   - Input characters and their frequencies into a hashmap to keep track of each character’s frequency.

2. **Construct the Huffman Tree**:
   - Use a priority queue to store nodes by frequency, with the lowest frequency at the top.
   - Repeatedly merge the two nodes with the lowest frequency until one node remains, representing the root of the Huffman Tree.

3. **Generate Codes**:
   - Traverse the tree from the root, assigning "0" for left branches and "1" for right branches to generate the binary codes for each character.
   - Store these codes in a hashmap for quick access.

4. **Display Codes**:
   - Output each character and its corresponding Huffman code.

### Algorithm Analysis

1. **Time Complexity**:
   - **Building Frequency Map**: \(O(n)\), where \(n\) is the number of characters.
   - **Building the Tree**: \(O(n \log n)\), due to the priority queue operations.
   - **Generating Codes**: \(O(n)\), because we only traverse the tree once to assign codes.
   - **Overall**: \(O(n \log n)\), primarily dominated by the tree-building step.

2. **Space Complexity**:
   - **Storage for Tree and Maps**: \(O(n)\), to store the frequency map, priority queue, and generated codes.
   - **Overall**: \(O(n)\), as each character has its code and node representation in the tree.

### Real-World Applications

- **File Compression**: Used in ZIP and GZIP file compression.
- **Multimedia Compression**: Widely applied in JPEG and MP3 formats to compress image and audio data.
- **Network Communications**: Reduces data size for efficient transmission, as in HTTP data compression.

This summary provides an understanding of Huffman Encoding and its greedy approach, steps, and algorithm complexity.
*/