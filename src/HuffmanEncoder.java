import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/*
 *This class creates a Huffman Encoder to compress and decode a text file
 * It uses a hashtable or orderedlist of nodes
 * @author Kevin Kulman
 * @version 6/4/22
 *
 */
public class HuffmanEncoder {
    private String inputFileName = "WarAndPeace.txt"; //"/WarAndPeace.txt"; //filename for uncompressed input file
    private String outputFileName = "WarAndPeace-compressed.bin"; //filename for compressed output file
    private String codesFileName = "WarAndPeace-codes.txt"; //filename for codes output file
    private BookReader book; //A book reader initialized with the inputFileName.
    private MyOrderedList<FrequencyNode> frequencies; //A list with frequency of each character in input file.
    private HuffmanNode huffmanTree; //The root of the Huffman tree
    private MyOrderedList<CodeNode> codes; //list that stores the codes assigned to each character
    private byte[] encodedText; //The encoded binary string stored as an array of bytes.

    //Use for hashtable functionality
    private boolean wordCodes = true;// Determines if the HuffmanEncoder uses character codes
                                     //or word and separator codes.
    private MyHashTable<String, Integer> frequenciesHash; //Hash table that stores the
                                            //frequency of each word or separator in the input file.
    private MyHashTable<String, String> codesHash; //Hash table that stores the codes
                                    //assigned to each word and separator by the Huffman algorithm.

    //HuffmanEncoder - The constructor should call the helper methods in the correct
    //order to carry out Huffman’s algorithm.
    public HuffmanEncoder(){

        frequencies = new MyOrderedList<>();
        codes = new MyOrderedList<>();
        try {
            book = new BookReader(inputFileName);
        } catch (IOException e){
            System.out.println("Error could not read file");
        }
        if (wordCodes){
            frequenciesHash = new MyHashTable<>(32768);
            codesHash = new MyHashTable<>(32768);
            countFrequencyHashTable();
        } else {
            countFrequency();
        }
        System.out.print("Building a Huffman tree and reading codes...");
        long startTime = System.currentTimeMillis(); //START TIMING METHOD
        if (wordCodes){
            buildTreeHashTable();
        } else {
            buildTree();
        }
        extractCodes(huffmanTree, "");
        //System.out.println(codesHash.toString());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("in " + duration + " milliseconds");
        encode();
        writeFiles();
    }


    //countFrequency - This method counts the frequency of each character in the book
        //and stores it in frequencies.
    //○ The counts are stored in FrequencyNodes.
    private void countFrequency() {
        System.out.print("Counting frequency of characters...");
        long startTime = System.currentTimeMillis(); //START TIMING METHOD
        for(int i = 0; i < book.book.length(); i++){
            char c = book.book.charAt(i);
            int index = frequencies.binarySearchReturnIndex(new FrequencyNode(c));
            if (index != -1) {
                frequencies.get(index).count++;
            } else {
                frequencies.add(new FrequencyNode(c));
            }
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime); //in milliseconds
        System.out.println("" + frequencies.size() + " unique characters found in " + duration + " milliseconds");
    }

    private void countFrequencyHashTable() {
        System.out.println("Counting frequency of words...");
        long startTime = System.currentTimeMillis(); //START TIMING METHOD
        book.wordsAndSeparators.first();
        for(int i = 0; i < book.wordsAndSeparators.size(); i++){
            String key = book.wordsAndSeparators.current();
            Integer value = frequenciesHash.get(key);
            if(value == null) value = 0;
            frequenciesHash.put(key, value+1);
            book.wordsAndSeparators.next();
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime); //in milliseconds
        //System.out.println(frequenciesHash.toString());
        System.out.println("Unique words/separators: " + frequenciesHash.size() + " in " + duration + " milliseconds");
    }

    //buildTree - This method builds the Huffman tree and extracts the codes from it,
        //○ Create a single Huffman node for each character weighted by its count.
        //○ Add all the nodes to a priority queue.
        //○ Merge Huffman nodes until only a single tree remains.
        //○ Store the root of the remaining tree in huffmanTree.
    private void buildTree() {
        MyPriorityQueue<HuffmanNode> q = new MyPriorityQueue<>();
        //Create and add the nodes to a priority queue
        for(int i = 0; i < frequencies.size(); i++){
            FrequencyNode n = frequencies.get(i);
            HuffmanNode node = new HuffmanNode(n.character, n.count);
            q.insert(node); //Add the node to a priority queue
        }
        //Merge Huffman nodes
        HuffmanNode mergeNode = null;
        while(q.size() > 1){ //take nodes from priority queue until empty
            mergeNode = new HuffmanNode(q.removeMin(), q.removeMin());
            q.insert(mergeNode);
        }

        huffmanTree = mergeNode; //set root of tree
    }

    //buildTree for Hashtable
    //buildTree - This method builds the Huffman tree and extracts the codes from it,
    //○ Create a single Huffman node for each word weighted by its count.
    //○ Add all the nodes to a priority queue.
    //○ Merge Huffman nodes until only a single tree remains.
    //○ Store the root of the remaining tree in huffmanTree.
    private void buildTreeHashTable(){
        MyPriorityQueue<HuffmanNode> q = new MyPriorityQueue<>();
        //Create and add the nodes to a priority queue
        for(int i = 0; i < frequenciesHash.keys.size(); i++){
            String key = frequenciesHash.keys.get(i);
            Integer value = frequenciesHash.get(key);
            HuffmanNode node = new HuffmanNode(key, value);
            q.insert(node); //Add the node to a priority queue
        }

        //Merge Huffman nodes
        HuffmanNode mergeNode = null;
        while(q.size() > 1){ //take nodes from priority queue until empty
            mergeNode = new HuffmanNode(q.removeMin(), q.removeMin());
            q.insert(mergeNode);
        }
        huffmanTree = mergeNode; //set root of tree
    }

    //extractCodes - A recursive method that traverses the Huffman tree to extract the
    //codes stored in it.
    private void extractCodes(HuffmanNode root, String codes){
        //Post order traversal
        //StringBuilder sb = new StringBuilder();
        //sb.append(codes);
        if (root.left != null) extractCodes(root.left, codes + "0");
        if (root.right != null) extractCodes(root.right, codes + "1");
        if (root.right == null && root.left == null){
            if(wordCodes){
                codesHash.put(root.word, codes);
            } else {
                this.codes.add(new CodeNode(root.character, codes));
            }
        }
    }

    //encode - Uses the book and codes to create encodedText.
        //○ For each character in text, append the code to an intermediate string.
        //○ Convert the string of character into a list of bytes and store it in encodedText.
        //○ You can convert a string of ‘0’s and ‘1’s to a byte with this line:
    //byte b = (byte)Integer.parseInt(str,2);
    private void encode(){
        StringBuilder sb = new StringBuilder();
        System.out.print("Encoding message...");
        long startTime = System.currentTimeMillis(); //START TIMING METHOD
        if(wordCodes){ //if using hashtable implementation
            //put codes in String
            book.wordsAndSeparators.first();
            for(int i = 0; i < book.wordsAndSeparators.size(); i++){
                String key = book.wordsAndSeparators.current();
                String code = codesHash.get(key);
                sb.append(code);
                book.wordsAndSeparators.next();
            }
        } else {
            for(int i = 0; i < book.book.length(); i++){
                char c = book.book.charAt(i);
                int index = codes.binarySearchReturnIndex(new CodeNode(c, ""));
                String nodeString = codes.get(index).code;
                sb.append(nodeString);
            }
        }

        //create byte[] size
        String s = sb.toString();
        int remainder = s.length() % 8;
        if (remainder != 0){
            encodedText = new byte[s.length()/8+1];
        } else {
            encodedText = new byte[s.length()/8];
        }

        //store all codes as bytes into encodedText[] except for last set to
        //check for remainder
        int k = 0;
        for(int i = 0; i < encodedText.length-1; i++) {
            String nodeString = s.substring(k, k+8);
            k += 8;
            encodedText[i] = (byte) Integer.parseInt(nodeString, 2);
        }

        //Check for remainder bytes and add
        String nodeString = "";
        if(remainder != 0) {
            nodeString = s.substring(k, k+remainder);
        } else{
            nodeString = s.substring(k, k+8);
        }
        encodedText[encodedText.length-1] = (byte) Integer.parseInt(nodeString, 2);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("in " + duration + " milliseconds");
    }

    //writeFiles() - Writes the contents of encodedText to the outputFileName
        //and the contents of codes to codesFileName.
        //○ It should output the time it takes to write the files.
    private void writeFiles() {
        //Write encodedText to outputFileName (the compressed file)
        //Write codes to codesFileName (the code dictionary)

        System.out.println("Writing compressed file...");
        long startTime = System.currentTimeMillis(); //START TIMING METHOD
        try {
            FileOutputStream compressedFW = new FileOutputStream(outputFileName);
            FileWriter codesFW = new FileWriter(codesFileName);
            compressedFW.write(encodedText);
            codesFW.write(codesHash.toString());
            long endTime = System.currentTimeMillis(); //START TIMING METHOD
            long duration = endTime - startTime;
            System.out.println("Bytes: " + encodedText.length + " in " +
                            duration + " milliseconds");
            codesFW.close();
            compressedFW.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }


    private class FrequencyNode implements Comparable<FrequencyNode> {
        public Character character; // The character that this count is for
        public Integer count; // The count this character is for

        public FrequencyNode(Character character){
            this.character = character;
            this.count = 1;
        }

        //toString - This method returns the contents of the node in this format:
            //character:count
        public String toString(){
            return "" + character.toString() + ":" + count;
        }

        @Override
        public int compareTo(FrequencyNode o) {
            if(character < o.character) return -1;
            if(character > o.character) return 1;
            return 0;
        }
    }

    private class HuffmanNode implements Comparable<HuffmanNode>{

        public Character character; //character this node stores. only leaves store characters.
        public Integer weight; //weight of the Huffman tree rooted at this node.
        public HuffmanNode left; //The root of the left sub-tree.
        public HuffmanNode right; //The root of the right sub-tree.
        public String word; // The word or separator that this node stores.
                            // Only leaves store words or separators.
                            // Internal nodes have no characters.

        //HuffmanNode constructor for hashtable to store words
        public HuffmanNode(String word, Integer wt){
            this.word = word;
            weight = wt;
        }

        //Constructor for leaf nodes
        public HuffmanNode(Character ch, Integer wt){
            character = ch;
            weight = wt;
        }

        //Constructor for internal nodes
        public HuffmanNode(HuffmanNode left, HuffmanNode right){
            this.left = left;
            this.right = right;
            if(right == null){
                weight = left.weight;
            } else if(left == null){
                weight = right.weight;
            } else {
                weight = left.weight + right.weight;
            }
        }

        @Override
        public int compareTo(HuffmanNode o) {
            if(weight > o.weight) return 1;
            if(weight < o.weight) return -1;
            return 0;
        }

        public String toString(){
            if (wordCodes) return "" + word.toString() + ":" + weight; //if using hashtable
            return "" + character.toString() + ":" + weight;
        }

    }

    private class CodeNode implements Comparable<CodeNode>{
        public Character character;
        public String code;

        public CodeNode(Character character, String code){
            this.character = character;
            this.code = code;
        }

        public CodeNode(Character character){
            this.character = character;
        }

        @Override
        public int compareTo(CodeNode o) {
            if(character < o.character) return -1;
            if(character > o.character) return 1;
            return 0;
        }

        public String toString(){
            return "" + character.toString() + ":" + code;
        }
    }
}
