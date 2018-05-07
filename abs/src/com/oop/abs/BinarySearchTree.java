//package com.oop.abs;
////
////class Node {
////    Plane data;
////    Node parent;
////    Node left;
////    Node right;
////}
//
//public class BinarySearchTree {
//    Node root = null;
//    int size = 0;
//
//    BinarySearchTree(){
//    }
//
//    public Node insert(Plane plane) {
//
//        Node node = getRoot();
//
////        System.out.println("BST says: found node: " + node);
//
//        if (node == null){
//            System.out.println("FIRST NODE. Inserting:  " + plane.name + " capacity: " + plane.capacity + " at ROOT");
//            this.root = createNode(plane);
//            return this.root;
//        }
//
//        if (plane.capacity < node.data.capacity){
//            System.out.println("LEFT NODE plane capacity: " + plane.capacity + " root capacity: " + root.data.capacity);
//            node.left = createNode(plane);
//        }
//        else {
//            System.out.println("RIGHT NODE plane capacity: " + plane.capacity + " root capacity: " + root.data.capacity);
//            node.right = createNode(plane);
//        }
//        System.out.println("returning node");
//        return node;
//    }
//
//    private Node createNode(Plane plane){
//        Node node = new Node();
//        node.data = plane;
//        node.left = null;
//        node.right = null;
//
//        size ++;
//        return node;
//    }
//
//    private Node getRoot(){
//        return this.root;
//    }
//}
