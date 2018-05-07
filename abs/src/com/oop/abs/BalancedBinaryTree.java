package com.oop.abs;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


class Node {
    Plane data;
    Node left;
    Node right;

    Node(Plane data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

/***
 * This class takes a LinkedList of Planes and sorts it into a sorted LinkedList using a Comparator implementation
 * and Collections.sort.
 *
 * Once the data is sorted a BalancedBinaryTree is produced from the input.
 *
 * The algorithm used is the standard mechanism for creating a balance tree from sorted data:
 *
 * 1) Find the mid point of the array. (m = data start index (0) + data end index -1)/2
 * 2) Check recursion break condition isn't hit (start index > end index)
 * 3) set the root node data to the index of the midpoint
 * 3) recursively set root.left as F(start, midpoint -1)
 * 5) recursively set the root.right as F(midpoint +1, end)
 *
 */
public class BalancedBinaryTree {
    Node bst;

    BalancedBinaryTree(LinkedList<Plane> data) {

        /* sort the list using a custom Comparator function to sort the planes based on their capacity  -
        Here we need to make sue that the plane class extends Comparable and contains a compareTo function
        and that this Collection class contains a compare method with a Comparator.

        https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property */
        Collections.sort(data, new Comparator<Plane>() {
            @Override
            public int compare(Plane p1, Plane p2) {
                if (p1.capacity < p2.capacity) return -1;
                if (p1.capacity == p2.capacity) return 0;
                if (p1.capacity > p2.capacity) return 1;
                return 0;
            }
        });
        int start = 0;
        int end = data.size()-1;
        this.bst = recursiveAddNode(data, start, end);
    }

    /**
     * Build a balanced binary tree from an input sorted linkedlist of Planes.
     *
     * @param data - LinkedList of Plane objects
     * @param start - int should always be initalised to 0
     * @param end - int data.size()-1
     * @return A BalancedBinaryTree
     */
    private Node recursiveAddNode (LinkedList<Plane> data, int start, int end) {

        /* Break recursion */
        if (start > end){
            return null;
        }
        int rootIndex = (start + end)/2;

        /* set the root as the middle index of the data */
        Node root = new Node(data.get(rootIndex));

        /* recursively add left side */
        root.left = recursiveAddNode(data, start, rootIndex-1);

        /* after recursion, recursively add right side */
        root.right = recursiveAddNode(data, rootIndex +1, end);
        return root;
    }

    /**
     * Search a Node for a given value in order
     * * @param root - root of BST
     * @return - Node
     */
    public void inorder(Node root) {
        /* break recursion */
        if(root == null) {
            return;
        }
        inorder(root.left);
        System.out.println("Visted Node: " + root.data.name + " " + root.data.capacity);
        inorder(root.right);
    }

    /**
     * wrapper for private find function to return a Plane object to the System Manager
     * @param root
     * @param capacity - int value of plane required
     */
    public Plane searchCapacity(Node root, int capacity) throws NotFoundException {
        Node result = find(root, capacity);
        if (result == null){
            return null;
        }
        return result.data;
    }

    /**
     * Use recursively search for input value against the capacities of the planes in the nodes
     *
     * @param root
     * @param capacity - int value of plane required
     */
    private Node find(Node root, int capacity) {
    Node result = null;
        if(root.left != null) {
            result = find(root.left, capacity);
        }
        if(root.data.capacity >= capacity)
            return root;
        if(result == null && root.right != null)
            result = find(root.right, capacity);
        if (result != null && result.data.available) {
            return result;
        }
        return null;
    }
}
