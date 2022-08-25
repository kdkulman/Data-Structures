import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/*
 * Main method to test
 *
 */
public class main {

    public static int testCount = 0;
    public static int passed = 0;
    //Helper class to test
    public static void test(String actual, String expected){
        int tempTestCount = testCount+1;
        if(actual.equals(expected)) {
            System.out.println("Test " + tempTestCount + " passed!");
            passed++;
        } else{
            System.out.println("Test " + tempTestCount + " failed.");
        }
        System.out.println("Actual: " + actual + " | Expected: " + expected);
        System.out.println();
        testCount++;
    }

    public static void main(String[] args) throws IOException {
        //MyHashTable<String, Integer> hash = new MyHashTable<>(32768);

//        //RESULT
//        System.out.println("----------------------------");
//        System.out.println("Passed " + passed + "/" + testCount + " tests!");


//
    }
}
