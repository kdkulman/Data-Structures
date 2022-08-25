/*
 *This class builds a Move to Front self organizing list
 *@author Kevin Kulman
 *@version 4/3/22
 *
 */
public class MTFList<Type extends Comparable<Type>> {

    private MyLinkedList<Type> list;

    //Constructor
    public MTFList(){
        list = new MyLinkedList<>();
    }

    //add - Adds the item at the front of the list
    public void add(Type type){
        list.first();
        list.addBefore(type);
    }

    //remove - Removes the item if it exists. The item is found using the compareTo()
    //function and only the first occurrence is removed.
    public Type remove(Type type){
        list.first();
        for(int i = 0; i < size(); i++){
            if(list.current().compareTo(type) == 0){ //check if a match
                return list.remove();
            } else {
                list.next();
            }
        }
        return null;
    }

    //find - Searches the list for the item and returns the item if found; else null.
    //If the item is found it is moved to the front of the list. The item is found using
    //the compareTo() function and only the first occurrence is found and returned.
    public Type find(Type type){
        if(remove(type) == null) return null;
        add(type);
        return type;
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
