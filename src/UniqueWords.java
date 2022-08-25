import java.io.IOException;

/*
 * This class will calculate the number of unique words in a given text file
 * @author Kevin Kulman
 * @version 5/24/22
 *
 */
public class UniqueWords {

    private BookReader book = new BookReader("WarAndPeace.txt");

    //UniqueWords - Calls each of the addUniqueWords methods one at a time.
    public UniqueWords() throws IOException {
//        addUniqueWordsToLinkedList();
//        addUniqueWordsToArrayList();
//        addUniqueWordsToOrderedList();
//        addUniqueWordsToBST();
//        addUniqueWordsToAVL();
//        addUniqueWordsToHashTable();
        addUniqueWordsToTrie();
//        addUniqueWordsToHashTable2();


    }

    //Adds the unique words to a MyLinkedList.
    //a. Add only the unique words to the list
    //b. Sort the list of unique words.
    //This method should time both steps and output each runtime to the console.
    public void addUniqueWordsToLinkedList() {
        MyLinkedList<String> list = new MyLinkedList<String>();
        int uniqueWords = 0;
        String word = book.words.first();
        System.out.println();
        System.out.println("Adding unique words to LinkedList...");

        long startTime = System.currentTimeMillis(); //START TIMING METHOD
        while(word != null) {
            if(!list.contains(word)) {
                uniqueWords++;
                list.addToEnd(word);
            }
            word = book.words.next();
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime)/1000; //in seconds
        System.out.println("Unique words: " + uniqueWords + " found in " + duration + " seconds");
        System.out.print("Sorting LinkedList...");
        startTime = System.currentTimeMillis(); //START TIMING METHOD
        list.sort();
        endTime = System.currentTimeMillis();
        duration = (endTime - startTime)/1000; //in seconds
        System.out.println("in " + duration + " seconds");
        System.out.println("Comparisons: " + list.comparisons);


    }

    public void addUniqueWordsToArrayList() {
        MyArrayList<String> list = new MyArrayList<String>();
        int uniqueWords = 0;
        //Check if word is already included
        String word = book.words.first();
        System.out.println();
        System.out.println("Adding unique words to ArrayList...");
        long startTime = System.currentTimeMillis(); //START TIMING METHOD
        while(word != null) {
            if(!list.contains(word)) {
                uniqueWords++;
                list.insert(word);
            }
            word = book.words.next();
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime)/1000; //in seconds
        System.out.println("Unique words: " + uniqueWords + " found in " + duration + " seconds");
        System.out.print("Sorting ArrayList...");



        startTime = System.currentTimeMillis(); //START TIMING METHOD
        list.sort();
        endTime = System.currentTimeMillis();
        duration = (endTime - startTime); //in milliseconds
        System.out.println("in " + duration + " milliseconds");
        System.out.println("Comparisons: " + list.comparisons);

    }

    public void addUniqueWordsToOrderedList() {
        MyOrderedList<String> list = new MyOrderedList<String>();
        int uniqueWords = 0;
        //Check if word is already included
        String word = book.words.first();
        System.out.println();
        System.out.println("Adding unique words to OrderedList...");
        long startTime = System.currentTimeMillis(); //START TIMING METHOD
        while(word != null) {
            if(list.binarySearch(word) == null) {
                uniqueWords++;
                list.add(word);
            }
            word = book.words.next();
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime)/1000; //in seconds
        System.out.println("Unique words: " + uniqueWords + " found in " + duration + " seconds");
        System.out.println("Comparisons: " + list.comparisons);
    }

    //addUniqueWordsToBST - Adds the unique words to a MyBinarySearchTree.
        //a. For each word in the list stored in book:
            //■ Check to see if the word is stored in the binary search tree of unique
            //words.
            // ● If it is not, add it to the binary search tree.
            //● Otherwise, skip this word.
        //b. Calls toString from the binary search tree to extract the words in order
    public void addUniqueWordsToBST(){
        MyBinarySearchTree<String> tree = new MyBinarySearchTree<String>();
        int uniqueWords = 0;
        //Check if word is already included
        String word = book.words.first();
        System.out.println();
        System.out.println("Adding unique words to Binary Search Tree...");
        long startTime = System.currentTimeMillis(); //START TIMING METHOD
        while(word != null) {
            if(tree.find(word) == null) {
                uniqueWords++;
                tree.add(word);
            }
            word = book.words.next();
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime); //in seconds
        System.out.println("Unique words: " + uniqueWords + " found in " + duration + " milliseconds");
        System.out.println("Comparisons: " + tree.comparisons);
        System.out.println("Tree Depth: " + tree.height());
        //System.out.println(tree.toString());
        //System.out.println(book.words.toString());
    }

    public void addUniqueWordsToAVL(){
        MyBinarySearchTree<String> treeAVL = new MyBinarySearchTree<String>(true);
        int uniqueWords = 0;
        //Check if word is already included
        String word = book.words.first();
        System.out.println();
        System.out.println("Adding unique words to AVL Tree...");
        long startTime = System.currentTimeMillis(); //START TIMING METHOD
        while(word != null) {
            if(treeAVL.find(word) == null) {
                uniqueWords++;
                treeAVL.add(word);
            }
            word = book.words.next();
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime); //in seconds
        System.out.println("Unique words: " + uniqueWords + " found in " + duration + " milliseconds");
        System.out.println("Comparisons: " + treeAVL.comparisons);
        System.out.println("Tree Depth: " + treeAVL.height());
        System.out.println("Rotations: " + treeAVL.rotations);
        //System.out.println(treeAVL.toString());
        //System.out.println(book.words.toString());
    }

    public void addUniqueWordsToHashTable(){
        MyHashTable<String, Integer> hash = new MyHashTable<>(32768);
        int uniqueWords = 0;
        //Check if word is already included
        String word = book.words.first();
        System.out.println();
        System.out.println("Adding unique words to Hash Table...");
        long startTime = System.currentTimeMillis(); //START TIMING METHOD
        while(word != null) {
            if(hash.get(word) == null) {
                hash.put(word, 1);
                uniqueWords++;
            }
            word = book.words.next();
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime); //in seconds
        System.out.println("Unique words: " + uniqueWords + " found in " + duration + " milliseconds");
        System.out.println("Comparisons: " + hash.comparisons);
        System.out.println("Max probe: " + hash.maxProbe);
    }

    public void addUniqueWordsToTrie(){
        MyTrie<String> trie = new MyTrie<String>();
        int uniqueWords = 0;
        //Check if word is already included
        String word = book.words.first();
        System.out.println();
        System.out.println("Adding unique words to Trie...");
        long startTime = System.currentTimeMillis(); //START TIMING METHOD
        while(word != null) {
            if(trie.find(word) == false) {
//                System.out.println("Find comps: " + trie.comparisons);
                uniqueWords++;
                trie.insert(word);
//                System.out.println(word + " " + trie.comparisons);
//                System.out.println(trie.toString());
            }

            word = book.words.next();
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime); //in seconds
        System.out.println("Unique words: " + uniqueWords + " found in " + duration + " milliseconds");
        System.out.println("Comparisons: " + trie.comparisons);
        System.out.println("comparisons " + trie.comparisons + " - Should be 37");

    }

    public void addUniqueWordsToHashTable2(){
        MyHashTable<String, Integer> hash = new MyHashTable<>(32768, true);
        int uniqueWords = 0;
        //Check if word is already included
        String word = book.words.first();
        System.out.println();
        System.out.println("Adding unique words to Hash Table (chaining)...");
        long startTime = System.currentTimeMillis(); //START TIMING METHOD
        while(word != null) {
            if(hash.get(word) == null) {
                hash.put(word, 1);
                uniqueWords++;
            }
            word = book.words.next();
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime); //in seconds
        System.out.println("Unique words: " + uniqueWords + " found in " + duration + " milliseconds");
        System.out.println("Comparisons: " + hash.comparisons);
        System.out.println("Max probe: " + hash.maxProbe);
    }

}
