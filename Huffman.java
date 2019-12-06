import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

class Node {
  int frequency;
  char character;
  Node left = null;
  Node right = null;

  Node(char ch, int freq) {
    this.character = ch;
    this.frequency = freq;
  }

  public Node(char ch, int freq, Node left, Node right) {
    this.character = ch;
    this.frequency = freq;
    this.left = left;
    this.right = right;
  }

  public void displayNode() {
    System.out.print("[" + this.character + ": " + this.frequency + "]");
  }
}

class Huffman {
  Node root;
  String str;

  // Stores the frequency of each character
  Map<Character, Integer> frequency = new HashMap<>();

  // Store the Huffman Codes
  Map<Character, String> HuffmanCode = new HashMap<>(); 

  // Create a Priority queue that stores the nodes of the Huffman tree
  // Remember the nodes with the Lowest frequency have the highest priority
  PriorityQueue<Node> pq = new PriorityQueue<>((l,r) -> l.frequency - r.frequency);

  
  public Huffman(String str) {
    this.str = str;
    root = null;
  }
  
  // Recursievly traverse the tree, create and store the resulting huffman codes
  public void encode(Node root, String str) {
    if(root == null) {
      return;
    }

    // If leaf Nodes is found then add it to the HuffmanCode
    if(root.right == null && root.left == null) {
      HuffmanCode.put(root.character, str);
    }

    encode(root.left, str + "0"); // Adds a 0 bit to the following Node
    encode(root.right, str + "1"); // Adds a 1 bit to the following Node
  }

  // Recursively traverse the Huffman tree and decode the string
  public int decode(Node root, int index, String str) {
    if(root == null) {
      return index;
    }

    if(root.left == null && root.right == null) {
      System.out.print(root.character);
      return index;
    }

    index++;

    if(str.charAt(index) == '0') {
      index = decode(root.left, index, str);
    } else {
      index = decode(root.right, index, str);
    }
    
    return index;
  }

  public void countCharacter() {
    for(int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      // If c doesn't exist in the HashMap the add it
      if(!frequency.containsKey(c)) {
        frequency.put(str.charAt(i), 0);
      }
      // Otherwise increment the existing value
      frequency.put(c, frequency.get(c)+1);
    }
  }

  // Creates all the leaf nodes and adds them to the Queue
  public void createPriorityQueue() {
    for(Map.Entry<Character, Integer> entry : frequency.entrySet()) {
      pq.add(new Node(entry.getKey(), entry.getValue()));
    }
  }

  // Group leaf nodes under internal nodes
  public void createInternalNode() {
    // Remove the lowest frequency
    // Also know as the highest priority in this case
    while(pq.size() != 1) {
      // 0 bit
      Node left = pq.poll();
  
      // 1 bit
      Node right = pq.poll();
  
      int sum = left.frequency + right.frequency;
      
      // Add back to pq
      pq.add(new Node('\0', sum, left, right));
    }
    this.root = pq.peek();
  }

  public void displayFrequency() {
    // Creates a set of exisitng keys and values in Frequency
    Set set = frequency.entrySet();

    // Create an iterator to iterate over Frequency
    Iterator i = set.iterator();

    while(i.hasNext()) {
      Map.Entry current = (Map.Entry)i.next();
      System.out.print(current.getKey() + ": ");
      System.out.println(current.getValue());
    }
  }

  public void displayEncoded() {
    Set set = HuffmanCode.entrySet();

    // Create an iterator to iterate over Frequency
    Iterator i = set.iterator();
    while(i.hasNext()) {
      Map.Entry current = (Map.Entry)i.next();
      System.out.print(current.getKey() + ": ");
      System.out.println(current.getValue());
    }
  }

  public String printEncoded() {
    String output = "";
    for(int i = 0; i < str.length(); i++) {
      output += HuffmanCode.get(str.charAt(i));
    }
    return output;
  }

  public void printDecoded(String s) {
    int index = -1;
    System.out.print("Decoded String: ");
    while(index < s.length()-2) {
      index = decode(this.root, index, s);
    }
    System.out.println("\n");
  }
}