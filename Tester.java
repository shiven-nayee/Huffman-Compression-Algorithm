class Tester {
  public static void main(String[] args) {
    Huffman tree = new Huffman("YEEEET");

    // Count characters and add to a hashmap
    tree.countCharacter();

    // Create a Priority Queue with leaf nodes
    tree.createPriorityQueue();

    // Create internal nodes and TREE
    tree.createInternalNode();

    // Encode charate into 0's and 1's
    tree.encode(tree.root, "");

    // Display encoded characters
    tree.displayEncoded();

    // Print encoded String
    String encodedString = tree.printEncoded();
    System.out.println("Encoded String: " + encodedString);

    tree.printDecoded(encodedString);
  }
}