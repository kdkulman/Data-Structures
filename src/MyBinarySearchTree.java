/*
 *This is a naive binary search tree created from the ground up
 *Includes AVL self balancing functionality
 *@author Kevin Kulman
 *@version 5/28/22
 *
 */

import java.util.Random;

public class MyBinarySearchTree<Type extends Comparable<Type>> {
    private Node root; //Stores the root node of the binary search tree
    private int size; // Stores the number of items stored in the binary search tree.
    public long comparisons; //Stores the number of comparisons made in the find method
    public Integer rotations; //Stores the number of rotations made
    private boolean balancing; //rotations- If true the binary search tree
                               // will rebalance itself after adds and removes.
                               // Otherwise the binary search tree will not rebalance itself.

    //Constructor
    public MyBinarySearchTree(){
        this.balancing = false;
        rotations = 0;
    }

    //Self-balancing constructor
    public MyBinarySearchTree(boolean balancing){
        this.balancing = balancing;
        rotations = 0;
    }

    //add - Adds the item into the binary search tree where it belongs. The public method
        //should call the private recursive method on the root.
    public void add(Type item){
        root = add(item, root);
    }

    //private add - adds the item to the sub-tree (recursively) and
        //returns the root of the new sub-tree.
    private Node add(Type item, Node subTree){
        //1) Add item
        if (subTree == null){
            size++;
            return new Node(item);
        } else if (item.compareTo(subTree.item) > 0){ //if greater, go right
            subTree.right = add(item, subTree.right);
        } else if (item.compareTo(subTree.item) < 0) { //if less, go left
            subTree.left = add(item, subTree.left);
        }

        //2) Update height
        subTree.height = updateHeight(subTree);

        //3) Correct balance if AVL tree
        if (balancing) {
            subTree.balanceFactor = updateBalanceFactor(subTree);
            int balanceFactor = subTree.balanceFactor;
            if (balanceFactor > 1 && item.compareTo(subTree.left.item) < 0)
                return rotateRight(subTree);

            //Right-Right Case
            if (balanceFactor < -1 && item.compareTo(subTree.right.item) > 0)
                return rotateLeft(subTree);

            //Left-Right Case
            if (balanceFactor > 1 && item.compareTo(subTree.left.item) > 0) {
                subTree.left = rotateLeft(subTree.left);
                return rotateRight(subTree);
            }

            //Right-Left Case
            if (balanceFactor < -1 && item.compareTo(subTree.right.item) < 0) {
                subTree.right = rotateRight(subTree.right);
                return rotateLeft(subTree);
            }
        }

        return subTree;
    }

    //Remove item from tree
    public void remove(Type item){
        root = remove(item, root);
    }

    //private recursive remove method
    private Node remove(Type item, Node subTree){

        //1) Remove node
        if (subTree == null) {
            return null;
        }

        if (subTree.item.compareTo(item) == 0){
            //0 children
            if (subTree.right == null && subTree.left == null) {
                size--;
                return null;
            }
            //1 children
            if(subTree.left == null && subTree.right != null) {
                size--;
                return subTree.right;
            }
            if(subTree.right == null && subTree.left != null) {
                size--;
                return subTree.left;
            }
            //2 children
            Node temp = helperRemove(subTree.right);
            subTree.item = temp.item;
            subTree.right = remove(temp.item, subTree.right);

        } else if (item.compareTo(subTree.item) > 0) {
            //if greater, go right
            subTree.right = remove(item, subTree.right);
            subTree.height = updateHeight(subTree);
        } else if (item.compareTo(subTree.item) < 0){
            //if less, go left
            subTree.left = remove(item, subTree.left);
            subTree.height = updateHeight(subTree);
        }

        //2) Update height
        subTree.height = updateHeight(subTree);
        //3) Correct balance if AVL tree
        if (balancing) {
            subTree.balanceFactor = updateBalanceFactor(subTree);
            int balanceFactor = subTree.balanceFactor;
            if (balanceFactor > 1 && subTree.left.balanceFactor >= 0)
                return rotateRight(subTree);

            //Left-Right Case
            if (balanceFactor > 1 && subTree.left.balanceFactor < 0) {
                subTree.left = rotateLeft(subTree.left);
                return rotateRight(subTree);
            }

            //Right-Right Case
            if (balanceFactor < -1 && subTree.right.balanceFactor <= 0)
                return rotateLeft(subTree);

            //Right-Left Case
            if (balanceFactor < -1 && subTree.right.balanceFactor > 0) {
                subTree.right = rotateRight(subTree.right);
                return rotateLeft(subTree);
            }
        }
        return subTree;
    }


    public Node helperRemove(Node node) {
        Node current = node;
        while (current.left != null) current = current.left;
        return current;
    }

    //find - Returns the item found if the item is in the binary search tree and null
    //otherwise. The public method should call the private recursive method on the root.
    public Type find(Type item){
        if(isEmpty()) {
            comparisons++;
            return null; //if empty tree / not found
        }
        return find(item, root);
    }

    //This private find method searches the appropriate sub-tree recursively for the item.
    private Type find(Type item, Node subTree){
        comparisons++;
        //if greater, go right
        if (item.compareTo(subTree.item) > 0 && subTree.right != null) return find(item, subTree.right);
        //if less, go left
        if (item.compareTo(subTree.item) < 0 && subTree.left != null) return find(item, subTree.left);
        //if found
        if (item.compareTo(subTree.item) == 0) return item;

        //If not found
        comparisons++;
        return null;
    }

    //rebalance - Checks node for imbalance and if found performs the appropriate
    //rotations to correct it. This method should run in O(1) time.

    //****
    //private Node rebalance(Node node){
//        //There are four cases for AVL tree
//        if (node.balanceFactor < -1 && node.right.balanceFactor <= 0) {
//            //LEFT-LEFT CASE
//            return rotateLeft(node); //right heavy
//        } else if(node.balanceFactor < -1 && node.left.balanceFactor > 0) {
//            //LEFT-RIGHT CASE
//            node.right = rotateRight(node.right);
//            return rotateLeft(node);
//        } else if (node.balanceFactor > 1 && node.){
//            //RIGHT-RIGHT CASE
//            return rotateRight(node); //left heavy
//        } else if(node.balanceFactor > 1 && node.) {
//            //RIGHT-LEFT CASE
//
//        }
//        return node; //if not imbalanced, do nothing

//        if (node.balanceFactor < -1){
//            return rotateLeft(node); //right heavy
//        } else if (node.balanceFactor > 1){
//            return rotateRight(node); //left heavy
//        }
//        return node; //if not imbalanced, do nothing
    //}

    //Performs a left rotation on the node. Should be O(1) time.
    private Node rotateLeft(Node node){
        rotations++;
        Node nodeToReturn = node.right;
        node.right = nodeToReturn.left;
        nodeToReturn.left = node;
        node.height = updateHeight(node);
        nodeToReturn.height = updateHeight(nodeToReturn);
        node.balanceFactor = updateBalanceFactor(node);
        nodeToReturn.balanceFactor = updateBalanceFactor(nodeToReturn);
        return nodeToReturn;
    }

    //Performs a right rotation on the node. Should be O(1) time.
    private Node rotateRight(Node node){
        rotations++;
        Node nodeToReturn = node.left;
        node.left = nodeToReturn.right;
        nodeToReturn.right = node;
        node.height = updateHeight(node);
        nodeToReturn.height = updateHeight(nodeToReturn);
        node.balanceFactor = updateBalanceFactor(node);
        nodeToReturn.balanceFactor = updateBalanceFactor(nodeToReturn);
        return nodeToReturn;
    }

    //update height after add or remove
    private int updateHeight(Node node){
        if(node.left == null && node.right == null) return 0;
        if(node.left == null) return node.right.height + 1;
        if(node.right == null) return node.left.height + 1;
        return Math.max(node.left.height, node.right.height) + 1;
    }

    //update balanceFactor after add or remove
    private int updateBalanceFactor(Node node){
        if(node.left == null && node.right == null){
            return 0;
        } else if(node.left == null){
            return (-1) * (node.right.height + 1);
        } else if (node.right == null){
            return node.left.height + 1;
        } else {
            return node.left.height - node.right.height;
        }
    }

    //Returns the height of the binary search tree
    public int height(){
        if (root == null) return -1;
        return root.height;
    }

    //Return true if tree is empty
    public boolean isEmpty(){
        if (size == 0) return true;
        return false;
    }

    //Get size of tree
    public int size(){
        return size;
    }

    //toString - Returns the contents of the binary search tree, in ascending order, as a
    //string. This method should run in O(n) time where n is the number of items stored in the
    //tree
    public String toString(){
        if(isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        String str = toStringHelper(root, sb) + "]";
        str = "[" + str.substring(2, str.length());
        return str;
    }

    private String toStringHelper(Node subTree, StringBuilder sb){
        //in order traversal
        if(subTree.left != null){
            toStringHelper(subTree.left, sb);
        }

        sb.append(", ");
        sb.append(subTree.toString());

        if(subTree.right != null){
            return toStringHelper(subTree.right, sb);
        }
        return sb.toString();
    }

    private class Node {
        public Type item; //item stored in Node
        public Node left; //left subtree
        public Node right; //right subtree
        public int height; //Height - (the distance to the leaf nodes). We will count edges.
        public int balanceFactor; //The balance factor of the node.
            //Defined as the height of the left subtree minus the height of the right subtree.

        //Node constructor
        public Node(Type item){
            this.item = item;
            height = 0;
            balanceFactor = 0;
        }

        public String toString(){
            return "" + item.toString() + ":H" + height + ":B" + balanceFactor;
        }
    }
}