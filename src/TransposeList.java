/*
 * This class creates a Transpose List out of an existing LinkedList
 * @author Kevin Kulman
 * @version 4/9/22
 *
 */

public class TransposeList<Type extends Comparable<Type>> {

    private MyLinkedList<Type> list;

    //Constructor
    public TransposeList(){
        list = new MyLinkedList<>();
    }

    //add - Adds the item at the end of the list
    public void add(Type type){
            list.addToEnd(type);
    }

    //remove - Removes the item if it exists. The item is found using the compareTo()
    //function and only the first occurrence is removed.
    public Type remove(Type type){
        list.first();
        Type returnNode = null;
        boolean isRemoved = false; //Keeps track if already removed
        for(int i = 0; i < size(); i++){
            if(list.current().compareTo(type) == 0 && isRemoved == false){ //check if a match
                returnNode = list.remove();
                isRemoved = true;
            } else {
                list.next();
                //For loop keeps going to let current go back to last/tail
            }
        }
        return returnNode;
    }

    //find- Searches the list for the item and returns the item if found; else null
    //If the item is found it is swapped with its neighbor towards the front of the list.
    //The item is found using the compareTo() function and only the first occurrence is found
    //and returned. This method should run in O(n) time.
    public Type find(Type type){
        list.first();
        Type returnNode = null;
        boolean isSwapped = false; //Keeps track if already swapped
        for(int i = 0; i < size(); i++){
            if(list.current().compareTo(type) == 0 && isSwapped == false){ //check if a match
                isSwapped = true;
                returnNode = type;
                if(i != 0) list.swapWithPrevious();
            } else {
                list.next(); //Keep cycling through to keep current always in last place
            }
        }
        return returnNode;
    }

    //Returns the size of the list
    public int size(){
        return list.size();
    }

    //isEmpty - Returns true if the size is 0 and false otherwise.
    public boolean isEmpty(){
        return list.isEmpty();
    }

    //toString - Returns a string that has the contents of the list
    public String toString(){
        return list.toString();
    }

}
