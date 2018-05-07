package com.oop.abs;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


class Node {
    Plane data;
    Node left;
    Node right;

    Node(Plane data){
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class BalancedBinaryTree {

    BalancedBinaryTree (LinkedList<Plane> data) {

        /* sort the list using a custom Comparator function to sort the planes based on their capacity  -
        Here we need to make sue that the plane class extends Comparable and contains a compareTo function
        and that this Collection class contains a compare method with a Comparator.

        https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property */
        Collections.sort(data, new Comparator<Plane>()
        {
            @Override
            public int compare(Plane p1, Plane p2)
            {
                if (p1.capacity < p2.capacity) return -1;
                if (p1.capacity == p2.capacity) return 0;
                if (p1.capacity > p2.capacity) return 1;
                return 0;
            }
        });
        for (Plane plane: data){
            System.out.println(plane.capacity);
        }
        Node root = recursiveAddNode(data);
    }

    private Node recursiveAddNode (LinkedList<Plane> data){
        if (data != null) {
            int middleIndex = (int) Math.floor(data.size() / 2);
            System.out.println("middle index: " + (int) Math.floor(data.size() / 2));
            System.out.println("middle index: " + data.get(middleIndex).capacity);

            Node root = new Node(data.get(middleIndex));
            LinkedList<Plane> leftData = new LinkedList<>();
            LinkedList<Plane> rightData = new LinkedList<>();

            /* Create a left subset of the data by slicing all indexes left of the middleIndex */
            for (int i=0; i==middleIndex-1; i++) {
                leftData.add(data.get(i));
            }
            /* Create a right subset of the data by slicing all indexes right of the middleIndex */
            for (int i = middleIndex+1; i==data.size()-1; i++) {
                rightData.add(data.get(i));
            }
            System.out.println("left size: " + leftData.size());
            System.out.println("right size: " + rightData.size());
            root.left = recursiveAddNode(leftData);
            root.right = recursiveAddNode(rightData);
            return root;
        }
        else return null;
    }
}
