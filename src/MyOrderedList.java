/*
 *This class is an ordered list data structure based off an ArrayList
 *@author Kevin Kulman
 *@version 6/4/22
 *
 */

public class MyOrderedList<Type extends Comparable<Type>> {
    private MyArrayList<Type> list;
    public long comparisons;

    public MyOrderedList(){
        list = new MyArrayList<>();
    }

    //add - Adds the item to the position of the list where it belongs.
    public void add(Type type){
        //comparisons++;
        list.insert(type);
        for(int i = size()-1; i > 0; i--) {
            //comparisons++;
            if (list.get(i).compareTo(list.get(i-1)) < 0) {
                //swap
                Type temp = list.get(i);
                list.set(i, list.get(i-1));
                list.set(i-1, temp);
            } else {
                break;
            }
        }
    }

    //remove - Removes the item from the list if found.
    public Type remove(Type type){
        for(int i = 0; i < size(); i++)
            if(list.get(i).compareTo(type) == 0) return list.remove(i);
        return null; //not found
    }

    //Helper method for remove; Returns index if found, otherwise returns -1
    public int removeBinarySearch(Type type, int start, int finish){
        int mid = finish/2;
        Type target = list.get(mid);
        if(target.compareTo(type) == 0) return mid;
        if(start == finish && target.compareTo(type) != 0) return -1;
        if(target.compareTo(type) >= 0) return removeBinarySearch(type, start, mid);
        if(target.compareTo(type) < 0) return removeBinarySearch(type, mid+1, finish);
        return -1;
    }

    //+binarySearch - Uses a binary search to search the list for item and returns true
    //if found, and false, otherwise.
    public Type binarySearch(Type type){
        comparisons++;
        if (isEmpty()) return null; //Check if empty list
        //list.sort();
        return binarySearch(type, 0, size()-1);
    }

    //Helper method for recursive binarySearch
    private Type binarySearch(Type type, int start, int finish){
        int mid = (start+finish)/2;
        Type target = list.get(mid);
//        comparisons++;
        if(target.compareTo(type) == 0) return type;
        //comparisons++;
        if(start == finish && target.compareTo(type) != 0) return null;
        comparisons++;
        if(target.compareTo(type) >= 0) return binarySearch(type, start, mid);
        return binarySearch(type, mid+1, finish);
    }

    //+binarySearch - Uses a binary search to search the list for item and returns index
    //THIS IS HELPFUL FOR HUFFMAN ENCODER
    //if found, and false, otherwise.
    public int binarySearchReturnIndex(Type type){
        if (isEmpty()) return -1; //Check if empty list
        return binarySearchReturnIndex(type, 0, size()-1);
    }

    //Helper method for recursive binarySearch
    private int binarySearchReturnIndex(Type type, int start, int finish){
        int mid = (start+finish)/2;
        Type target = list.get(mid);
        if(target.compareTo(type) == 0) return mid;
        if(start == finish && target.compareTo(type) != 0) return -1;
        if(target.compareTo(type) >= 0) return binarySearchReturnIndex(type, start, mid);
        return binarySearchReturnIndex(type, mid+1, finish);
    }

    // get - Returns the element stored at index and null if the index is out of bounds.
    public Type get(int index){
        return list.get(index);
    }

    public void set(int index, Type type){
        list.set(index, type);
    }

//    //set - locates an element and updates it, adds the element if not found
//    public Type set(Type type){
//
//    }

    //size - return size of the list
    public int size(){
        return list.size();
    }

    //isEmpty - Returns true if the size is 0 and false otherwise
    public boolean isEmpty(){
        return list.isEmpty();
    }

    //Returns a string that has the contents of the list separated by commas
    //and spaces and enclosed in square brackets.
    public String toString(){
        return list.toString();
    }

}
