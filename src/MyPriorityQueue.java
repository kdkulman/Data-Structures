/*
 * This class creates a min heap priority queue from the ground up
 * @author Kevin Kulman
 * @version 4/17/22
 *
 */
public class MyPriorityQueue <Type extends Comparable<Type>>{

    //heap - Stores the values in the heap in an underlying MyArrayList.
    private MyArrayList<Type> heap;

    //Constructor
    public MyPriorityQueue(){
        heap = new MyArrayList<>();
    }

    //insert - Inserts the item into the heap and corrects the invariant
    //1) Inserts item at end of the array list
    //2) Calls bubbleUp to move item to correct location.
    public void insert(Type type){
        heap.insert(type); //inserts at end of list
        bubbleUp(); //put into correct spot
    }

    //removeMin - Removes the first element and corrects the invariant.
        //a. Swaps the first element with the last element.
        //b. Removes the last element.
        //c. Calls sinkDown to move the first element to the correct location.
        //d. Returns the element removed.
    public Type removeMin(){
        Type min = min();
        //Swap first element with last element
        swap(heap.get(0), heap.get(size()-1), 0, size()-1);
        //Remove last element
        heap.remove(size()-1);
        sinkDown();
        return min;
    }

    //sinkDown - Shifts the first element down to where it belongs.
    //It does so by swapping the element with its smallest child if they are out
    //of order
    private void sinkDown() {
        sinkDownRecursive(0);
    }

    //Helper method for sinkdown recursion
    private void sinkDownRecursive(int parentIndex){
        int leftChildIndex = left(parentIndex);
        int rightChildIndex = right(parentIndex);
        Type leftChild = heap.get(leftChildIndex);
        Type rightChild = heap.get(rightChildIndex);
        Type parent = heap.get(parentIndex);

        //check if both children do not exist
        if(leftChildIndex == -1 && rightChildIndex == -1) return;
        //check if only right exists
        if(leftChildIndex == -1) {
            if(rightChild.compareTo(parent) < 0){
                //SWAP
                swap(rightChild, parent, rightChildIndex, parentIndex);
                sinkDownRecursive(rightChildIndex);
            } else {
                //DON'T SWAP, ALL DONE
                return;
            }
            //check if only left exists
        } else if(rightChildIndex == -1) {
            if(leftChild.compareTo(parent) < 0){
                //SWAP
                swap(leftChild, parent, leftChildIndex, parentIndex);
                sinkDownRecursive(leftChildIndex);
            } else {
                //DON'T SWAP, ALL DONE
                return;
            }
            //since both children exist
        } else {
            int childToSwapIndex;
            childToSwapIndex = leftChild.compareTo(rightChild) < 0 ?leftChildIndex : rightChildIndex;
            Type childToSwap = heap.get(childToSwapIndex);
            if(childToSwap.compareTo(parent) < 0) { //SWAP
                swap(childToSwap, parent, childToSwapIndex, parentIndex);
                sinkDownRecursive(childToSwapIndex);
            }
        }
    }

    //Returns the first element
    public Type min(){
        if(size() == 0) return null;
        return heap.get(0);
    }


    //Returns the index of the left child node in the heap of the index passed in.
    //return -1 if does not exist
    private int left(int parentIndex){
        int leftChildIndex = 2*parentIndex+1;
        if(leftChildIndex >= size()) leftChildIndex = -1; //index does not exist
        return leftChildIndex;
    }

    //Returns the index of the right child node in the heap of the index passed in.
    //return -1 if does not exist
    private int right(int parentIndex){

        int rightChildIndex = 2*parentIndex+2;
        if(rightChildIndex >= size()) rightChildIndex = -1; //index does not exist
        return rightChildIndex;
    }

    //parent - Returns the index of the parent node in the heap.
    private int parent(int index){
        int parentIndex = (index - 1) / 2;
        return parentIndex;
    }

    //bubbleUp - Shifts last element up to where it belongs to correct the invariant
    //It does so by swapping the element with its parent if they are out of order
    //(using the compareTo method).
    private void bubbleUp(){
        int childIndex = size() - 1;
        int parentIndex = parent(childIndex);
        Type parent = heap.get(parentIndex);
        Type child = heap.get(childIndex);
        while(child.compareTo(parent) < 0) {
            swap(child, parent, childIndex, parentIndex);
            childIndex = parentIndex;
            parentIndex = parent(parentIndex);
            parent = heap.get(parentIndex);
        }
    }

    //helper method for bubbleUp and sinkDown
    private void swap(Type child, Type parent, int childIndex, int parentIndex){
        heap.set(childIndex, parent);
        heap.set(parentIndex, child);
    }

    //isEmpty method
    public boolean isEmpty(){
        return heap.isEmpty();
    }

    //Size method
    public int size(){
        return heap.size();
    }

    //toString method
    public String toString(){
        return heap.toString();
    }
}
