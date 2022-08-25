/*
 * This class is an implementation of the Trie data structure
 * @author Kevin Kulman
 * @version 6/5/22
 *
 */

public class MyTrie<Type> {

    private Node root;
    private int size;
    public long comparisons;

    //constructor
    public MyTrie(){
        root = new Node('\0');
        comparisons = 0;
        size = 0;
//        root.addChildren();
    }

    //insert - Inserts the item into the trie. This method should run in O(m) time where m
    //is the length of item.
    public void insert(String item){
        Node node = root;
        int index;
        for (int i = 0; i < item.length(); i++) {
            comparisons++;
            if (node.children.isEmpty()) node.addChildren();
            index = node.children.binarySearchReturnIndex(new Node(item.charAt(i)));
            Node childNode = node.children.get(index);
            if (childNode.children.isEmpty()) childNode.addChildren();
            //Move down to the next node
            node = childNode;
        }
        if(node.terminal == false){
            node.terminal = true;
            size++; //increase size only if not duplicate word
        }
    }

    // remove - Removes the item from the trie.
    public void remove(String item){
        //look through children
        Node node = root;
        int index;
        MyOrderedList<Node> rootChildren = root.children;
        for (int i = 0; i < item.length(); i++) {
            index = rootChildren.binarySearchReturnIndex(new Node(item.charAt(i)));
            if(index == -1) return;
            Node childNode = node.children.get(index);
            //Move down to the next letter
            rootChildren = node.children;
            node = childNode;
        }
        if(node.terminal == true) {
            node.terminal = false;
            size--;
        }
    }

    //find - Returns true if the item is in the trie and false otherwise
    public boolean find(String item){
        //look through children
        Node node = root;
        int index;
        for (int i = 0; i < item.length(); i++) {
            comparisons++;
            if(node.children.isEmpty()) return false;
            index = node.children.binarySearchReturnIndex(new Node(item.charAt(i)));
            //Move down to the next node
            node = node.children.get(index);
            if(node.children.isEmpty() && i == item.length()-2 && item.length() > 6) return false;
        }
        if (node.terminal == true) return true;
        return false;
    }

    //toString - Returns the contents of the trie, in alphabetical order, as a string.
    // Uses the helper function addWords.
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(isEmpty()) return "[]";
        String str = "[" + addWords(root, "", sb);
        str = str.substring(0, str.length()-2) + "]";
        return str;
    }

    //addWords - This recursive function traverses the trie as a pre-order depth-first
    //traversal. The string str stores the path to the current node.
    // When words in the Trie are found,  they will be added to output.
    private String addWords(Node node, String str, StringBuilder output){
        //Pre order traversal
        //check if actually a word
        if (node.terminal) {
            output.append(str);
            output.append(", ");
        }
        MyOrderedList<Node> rootChildren = node.children;
        for (int i = 0; i < rootChildren.size(); i++) {
            addWords(rootChildren.get(i), str + rootChildren.get(i).character, output);
        }
        return output.toString();
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        if (size == 0) return true;
        return false;
    }

    private class Node implements Comparable<Node>{
        public boolean terminal; //- True if this node represents the end of a word
                    // stored in the trie, and false if the node is only part
                    // of a prefix of a word stored in the trie.
        public char character; //The character you must follow to arrive at this
                                // node from its parent
        public MyOrderedList<Node> children;// The list of children nodes, one for each

        //constructor
        public Node(char character){
            this.character = character;
            this.terminal = false;
            children = new MyOrderedList<>();
        }

        // addChildren - Adds one new node to children for each character in this set
        public void addChildren(){
            //Valid characters to use for the trie
            String validChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'";
            for (int i = 0; i < validChars.length(); i++) {
                Node node = new Node(validChars.charAt(i));
                children.add(node);
            }
        }

        public String toString(){
            return "" + character;
        }

        @Override
        //compare the nodes based on character
        public int compareTo(Node other) {
            if(Character.compare(character, other.character) > 0) return 1;
            if(Character.compare(character, other.character) < 0) return -1;
            return 0;
        }
    }
}
