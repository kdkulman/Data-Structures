/*
 * Implementation of an ArrayList
 * @author Kevin
 * @version 4/29/22
 *
 */

public class MyArrayList<Type extends Comparable<Type>> {

    private int capacity;
    private int size;
    private Type[] list;
    public long comparisons;

    //Constructor with initial size
    public MyArrayList(int size){
        this.size = size;
        capacity = size*2;
        list = (Type[]) new Comparable[capacity];
        comparisons = 0;
    }

    //Constructor without initial size
    public MyArrayList(){
        this.size = 0;
        this.capacity = 16;
        list = (Type[]) new Comparable[capacity];
        comparisons = 0;
    }

    //insert - Inserts the item at position index. Any elements after the inserted
    //element shuffle down one position. If the index is greater than the size then this
    //method does nothing.
    public void insert(Type type, int index){
        if (index >= 0 && index <= size) { //check if out of bounds
            if (size == capacity) resize(); //Check if list is full and resize
            for (int i = size; i > index; i--) { //Shuffle elements down
                list[i] = list[i - 1];
            }
            list[index] = type;
            size++;
        }
    }

    //insert - Inserts the item at end of array list - no index required
    public void insert(Type type){
        if (size == capacity) resize(); //Check if list is full and resize
        list[size] = type;
        size++;
    }

    //remove - Removes the element at position index and returns the element. Any
    //elements after the removed element shuffle down to fill the empty position. If index
    //is out of bounds this method does nothing and returns null.
    public Type remove(int index){
        //Check if index is out of bounds
        if(index < 0 || index >= size) return null;
        Type type = list[index];
        for(int i = index; i < size-1; i++){ //Shuffle elements down
            list[i] = list[i+1];
        }
        list[size-1] = null;
        size--;
        return type;
    }

    //contains - Searches the list for the item and returns true if found or false
    public boolean contains(Type type){
        comparisons++;
        for(int i = 0; i < size; i++){
            comparisons++;
            if(list[i].compareTo(type) == 0) return true; //0 is equals
        }
        return false;
    }

    //indexOf - Searches the list for the item and returns the index if found (and -1
    //otherwise). This is one of two standard searches in a list. This method should run in O(n)
    //time.
    public int indexOf(Type type){
        for(int i = 0; i < size; i++){
            if(list[i].compareTo(type) == 0) return i;
        }
        return -1;
    }

    // get - Returns the element stored at index and null if the index is out of bounds.
    public Type get(int index){
        //Check if index is out of bounds
        if(index < 0 || index > size) return null;
        return list[index];
    }

    //sort - Sorts the list in ascending order - merge sort
    public void sort(){
        if(!isEmpty()) merge(0, size-1);
    }

    //Helper method for sort
    private void mergeSort(int start, int finish){
        Comparable[] helper = new Comparable[finish+1];
        for(int i = start; i <= finish; i++){ //COPY INTO HELPER LIST
            helper[i] = get(i);
        }
        int mid = (start+finish)/2;
        int i = start;
        int j = mid + 1;
        int k = start;
        while(i <= mid && j <= finish) { //keep track of indices
            if (helper[i].compareTo(helper[j]) <= 0) {
                set(k, (Type) helper[i]);
                i++;
            } else {
                set(k, (Type) helper[j]);
                j++;
            }
            k++;
        }
        while (i <= mid){//ADD REST OF ELEMENTS IN
            set(k, (Type) helper[i]);
            k++;
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

    //set - Updates the element stored at index. Does nothing if index is out of bounds.
    public void set(int index, Type type){
        //Check if index is out of bounds
        if(index < 0 || index > size) {
            //do nothing
        } else {
            list[index] = type;
        }
    }

    //size - Returns the field size
    public int size(){
        return size;
    }

    //isEmpty - Returns true if the size is 0 and false otherwise
    public boolean isEmpty(){
        if (size == 0) return true;
        return false;
    }

    //toString - Returns a string that has the contents of the list separated by commas
    //and spaces and enclosed in square brackets.
    public String toString(){
        StringBuilder sb = new StringBuilder(); //StringBuilder less expensive in this case
        sb.append("[");
        for(int i = 0; i < size-1; i++){
            sb.append(list[i]);
            sb.append(", ");
        }
        if(size > 0 ) sb.append(list[size-1]);
        sb.append("]");
        return sb.toString();
    }

    //resize - Called by insert when the list is full. Doubles the capacity of the
    //list and copies the elements into a new array.
    private void resize(){
        capacity *= 2;
        Type[] list = (Type[]) new Comparable[capacity];
        //Copy elements into new array
        for(int i = 0; i < size; i++){
            list[i] = this.list[i];
        }
        this.list = list;
    }
}
