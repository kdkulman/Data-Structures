/*
 *This is a hash table data structure built from the ground up
 *Uses linear probing of step size 1 to handle collisions
 *Also uses chaining now
 *@author Kevin Kulman
 *@version 5/30/22
 */

import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class MyHashTable<Key extends Comparable<Key>, Value> {
    private Integer capacity;     //capacity of the hash table.
    private Key[] keyBuckets;     //stores the keys according to their hash value.
    private Value[] valueBuckets; //stores the values according to the hash value of its key
    private Integer size = 0;         //Number of key-value pairs stored in the table.
    public MyArrayList<Key> keys = new MyArrayList<>();; //list of unique keys stored in the hash table.
    public Integer comparisons = 0;   //total number of probes made when putting a new key-value pair.
    public Integer maxProbe = 0;      //maximum number of probes made to put any key-value pair.

    //chaining variables
    private boolean chaining;
    private MyArrayList<KeyValuePair>[] chainBuckets;


    //Constructor
    public MyHashTable(Integer capacity){
        this.chaining = false;
        this.capacity = capacity;
        keyBuckets = (Key[]) new Comparable[capacity];
        valueBuckets = (Value[]) new Object[capacity];
    }

    //Chaining constructor
    public MyHashTable(Integer capacity, boolean chaining){
        this.chaining = chaining;
        this.capacity = capacity;
        keyBuckets = (Key[]) new Comparable[capacity];
        valueBuckets = (Value[]) new Object[capacity];
    }

    //hash - This method will use the hashcode method of the key to produce an index in the
    //range 0-capacity. This method should run in O(1) time.
    private Integer hash(Key key){
        return Math.abs(key.hashCode() % capacity);
    }

    //get - This method returns the value associated with the key.
    //This method should run in O(1) time under uniform hashing assumptions.
        //a. Use hash to determine the index of the key.
        //b. Find the key in the hash table using probing if necessary. Use a step size of 1 for
            //probing.
        //c. Using the index of the found key, retrieve the value and return it.
        //d. If no key is found, return null.
    public Value get(Key key) {
        Integer index = hash(key);
        if(chaining) return chainingGet(key, index);
        return recursiveGet(key, index);
    }

    //helper method for get
    private Value recursiveGet(Key key, int index) {
        if (keyBuckets[index] == null) return null;
        if (keyBuckets[index].equals(key)) return valueBuckets[index];
        return recursiveGet(key, (index+1 % capacity));
    }

    //Chaining get method
    private Value chainingGet(Key key, int index){
        if (chainBuckets == null) return null;
        KeyValuePair newPair = new KeyValuePair(key, null);
        MyArrayList list = chainBuckets[index];
        for(int i = 0; i < list.size(); i++){
            Comparable pair = list.get(i);
            pair = ((KeyValuePair) pair);
            if (pair.compareTo(newPair) == 0){
                return newPair.value;
            }
        }
        return null;
    }

    //put - This method updates the value stored with the key.
    //This method should run in O(1) time under uniform hashing assumptions
        //a. Use hash to determine the index of the key.
        //b. Find the key, or the first null location, in the hash table using probing if necessary.
            //Use a step size of 1 for probing.
        //c. Using the index of the found location, write the value into the table.
    public void put(Key key, Value value){
        Integer index = hash(key);
        index = recursivePut(key, value, index, 1);
        keyBuckets[index] = key;
        valueBuckets[index] = value;
    }

    //helper method for Put
    private Integer recursivePut(Key key, Value value, Integer index, Integer probe) {
        maxProbe = Math.max(probe, maxProbe);
        if(keyBuckets[index] == null) {
            comparisons++;
            size++;
            keys.insert(key);
            return index;
        }
        if(keyBuckets[index].equals(key)) return index;
        comparisons++;
        return recursivePut(key, value, (index+1 % capacity), probe+1);
    }

    //size - Returns the size of the hash table.
    public Integer size(){
        return size;
    }

    //toString - Outputs the contents of the hash table in this format:
    //[key1:value1, key2:value2, … ]
    //This method should run in O(n) time where n is the capacity of the hash table.
    public String toString(){
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < capacity; i++) {
            if (keyBuckets[i] != null) {
                sb.append(keyBuckets[i] + ":" + valueBuckets[i]);
                sb.append(", ");
            }
        }
        String s = sb.substring(0,sb.length()-2);
        s = s + "]";
        return s;
    }

    private class KeyValuePair implements Comparable<KeyValuePair>{

        public Key key;
        public Value value;

        public KeyValuePair(Key key, Value value){
            this.key = key;
            this.value = value;
        }

        public String toString(){
            return key.toString() + ":" + value.toString();
        }

        public Value getValue() {
            return value;
        }

        public Key getKey(){
            return key;
        }

        @Override
        public int compareTo(KeyValuePair o) {
            if(key.compareTo(o.key) > 0) return 1;
            if(key.compareTo(o.key) < 0) return -1;

            return 0;
        }
    }
}