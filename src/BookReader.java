import java.awt.print.Book;
import java.io.*;
import java.util.Scanner;

/*
 *This class will read in a text file and parse the words
 *@author Kevin Kulman
 *@version 6/4/22
 *
 */
public class BookReader {

    public String book; //book - The book as a String.
    public MyLinkedList<String> words; //words - A list of all the words in the book.
    public MyLinkedList<String> wordsAndSeparators;
                            // A list that stores both the words and separators in the
                            //order that they appear in the book.

    public BookReader(String file) throws IOException {
        words = new MyLinkedList<>();
        wordsAndSeparators = new MyLinkedList<>();
        readBook(file);
        parseWords();
        //System.out.println(book);
    }

    //Reads the contents of the file into the book.
        //a. Opens the file filename.
        //b. Reads the file character by character.
        //c. Closes the file.
        //d. Stores the contents of the file in book.
    //This method should time itself and output its runtime to the console.
    public void readBook(String filename) throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            int c;
            long startTime = System.currentTimeMillis(); //START TIMING METHOD
            while((c = br.read()) != -1){
                sb.append( (char) c);
            }
            long endTime = System.currentTimeMillis();
            br.close(); //CLOSE FILE
            long duration = (endTime - startTime); //in milliseconds
            System.out.println("readBook method read " + sb.toString().length() +
                                " characters in " + duration + " milliseconds.");
            book = sb.toString();
        } catch (FileNotFoundException e){
            System.out.println("File could not be found");
        } catch (IOException e) {
            System.out.println("IOException occured.");
        }
    }

    //parseWords - Scans the book for words. When a word is found it is stored in words.
    public void parseWords() {
        try {
            BufferedReader br = new BufferedReader(new StringReader(book));
            StringBuilder sb = new StringBuilder();
            int ch;
            long startTime = System.currentTimeMillis(); //START TIMING METHOD
            while ((ch = br.read()) != -1) {
                char c = ((char) ch);
                if(isValidCharacterForWord(c)) {
                    sb.append(c);
                } else {
                    if(sb.toString().length() > 0) {
                        wordsAndSeparators.addToEnd(sb.toString());
                        words.addToEnd(sb.toString());
                        sb.setLength(0);
                    }
                    wordsAndSeparators.addToEnd("" + c);
                }
            }
            if (sb.toString().length() > 0) {
                wordsAndSeparators.addToEnd(sb.toString());
                words.addToEnd(sb.toString());
            }

            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime); //in milliseconds
            System.out.println("Read " + words.size() + " words in " + duration + " milliseconds.");
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found");
        } catch (IOException e) {
            System.out.println("IOException occured.");
        }
    }

    private boolean isValidCharacterForWord(Character ch){
        if(     (ch.compareTo('a') >= 0 && ch.compareTo('z') <= 0) ||
                (ch.compareTo('A') >= 0 && ch.compareTo('Z') <= 0) ||
                (ch.compareTo('0') >= 0 && ch.compareTo('9') <= 0) ||
                (ch.compareTo('\'')) == 0){

            return true;
        }
        return false;
    }
}
