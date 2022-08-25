/*
 * Implementation of a LinkedList
 * @author Kevin
 * @version 4/10/22
 *
 */

public class MyLinkedList<Type extends Comparable> {

    private Node first; //Reference to the first Node in the list
    private Node current; //Reference to the current Node in the list
    private Node previous; //Reference to the previous current Node in the list
    public long comparisons; //
    private int size;

    //Constructor
    public MyLinkedList(){
        first = null; //LinkedList begins empty
        size = 0;
        comparisons = 0;
    }

    //addBefore - Adds the item before the current Node.
    //If the current Node is null the new element is added in the last position.
    public void addBefore(Type type){
        Node node = new Node(type); //create node
        //Check if current node is null
        if (current == null){
            if(isEmpty()){ //Check if list is empty
                first = node;
                first.next = current;
                previous = first;
            } else {
                node.next = current;
                previous.next = node;
                previous = node;
            }
        } else {
            //Special case if current is first in list
            if (current == first){
                node.next = current;
                previous = node;
                first = node;
            } else {
                node.next = current;
                previous.next = node;
                previous = node;
            }
        }
        size++;
    }

    //addAfter - Adds item after the current Node. If current Node is null; does nothing.
    public void addAfter(Type type){
        if (current != null){
            Node node = new Node(type); //create node
            node.next = current.next;
            current.next = node;
            size++;
        }
    }

    //remove - Removes the current Node and returns the element. Any elements after
    //the removed element shuffle down to fill the empty position.
    //After this method the current Node will be equal to the node after the removed node.
    public Type remove(){
        if (current != null) {//If the current Node is null this method does nothing.
            Type removedNode = (Type) current.item;
            if(first == current) { //EDGE CASE
                current = current.next;
                first = current;
            } else {
                current = current.next;
                previous.next = current;
            }
            size--;
            return removedNode;
        }
        return null;
    }

    //current - Returns the item stored in the current Node. This method returns
    //null if the current Node is null.
    public Type current(){
        if(current == null) return null;
        return (Type) current.item;
    }

    //first - Sets the current Node to be the first Node and returns the item
    //stored in it. This method returns null if the first Node is null. This method
    //should run in O(1) time.
    public Type first(){
        if(first == null) return null;
        if(current == null) {
            current = first;
            return (Type) first.item;
        }
        current = first;
        previous = null;
        return (Type) first.item;
    }

    //next - Sets the current Node to be the next node in the list and returns the item
    //stored in it.
    public Type next(){
        if (current == null || current.next == null) {
            return null; //Returns null if the current Node is null.
        } else {
            previous = current;
            current = current.next;
            return (Type) current.item;
        }
    }

    //contains - Searches the Nodes for the item and returns true if found; else false
    public boolean contains(Type type){
        comparisons++;
        Node node = first;
        for(int i = 0; i < size; i++){
            Type theType = (Type) node.item;
            comparisons++;
            if (theType.compareTo(type) == 0){
                return true;
            }
            node = node.next;
        }
        return false;
    }

    //size - Returns the field size.
    public int size(){
        return size;
    }

    //isEmpty - Returns true if the size is 0 and false otherwise.
    public boolean isEmpty(){
        if ( size == 0) return true;
        return false;
    }

    //sort - Sorts the list in ascending order.
    public void sort(){
        if(!isEmpty()) merge(0, size-1);
    }

    //Helper method for sort
    private void mergeSort(int start, int finish){
        first();
        for(int i = 0; i < start; i++){ //move current to start
            next();
        }
        Node startNode = current;
        Comparable[] helper = new Comparable[finish+1];
        for(int i = start; i <= finish; i++){ //COPY INTO HELPER LIST
            helper[i] = current();
            next();
        }
        current = startNode;
        int mid = (start+finish)/2;
        int i = start;
        int j = mid + 1;

        while(i <= mid && j <= finish) { //keep track of indices
            if (helper[i].compareTo(helper[j]) <= 0) {
                current.item = (Type) helper[i]; //update actual linked list
                i++;
            } else {
                current.item = (Type) helper[j]; //update actual linked list
                j++;
            }
            next();
        }
        while (i <= mid){//ADD REST OF ELEMENTS IN LINKEDLIST
            current.item = (Type) helper[i];
            next();
            i++;
        }
    }

    //Helper method for sort
    private void merge(int start, int finish){
        if(finish > start){ //Check if the list is greater than size 1
            int mid = start + (finish - start) / 2;
            merge(start, mid);
            merge(mid+1, finish);
            mergeSort(start, finish);
        }
    }

    //toString - Returns a string that has the contents of the Nodes separated by
    //commas and spaces and enclosed in square brackets.
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node node = first;
        for(int i = 0; i < size-1; i++){
            sb.append(node.item);
            sb.append(", ");
            node = node.next;
        }
        if(size > 0) sb.append(node.item);
        sb.append("]");
        return sb.toString();

    }

    //Node Class for MyLinkedList
    private class Node<Type>{
        public Type item;
        public Node next;

        public Node (Type type){
            item = type;
        }

        //Returns the String of item
        public String toString(){
            return item.toString();
        }
    }

    //addToEnd - Adds the item to the End of the list.
    //Helper function for TransposeList
    public void addToEnd(Type type){
        if(isEmpty()){ //IF LIST IS EMPTY
            current = new Node(type); //create node
            first = current;
        } else {
            Node node = new Node(type); //create node
            current.next = node;
            current = node;
        }
        size++;
    }

    //Swaps the current Node with the previous Node - Helper method for find() in Transpose list
    //DO NOT MOVE NODES, MOVE THE ITEMS
    public Type swapWithPrevious(){
        if (previous != null){ //edge case if current is first element
            Type tempItem = (Type) current.item;
            current.item = previous.item;
            previous.item = tempItem;
            return tempItem;
        }
        return null;
    }
}