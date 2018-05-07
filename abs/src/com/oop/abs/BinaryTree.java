//package com.oop.abs;
//
//class Node {
//    Plane data;
//    Node left;
//    Node right;
//
//    Node (Plane plane){
//        this.data = plane;
//        printNode();
//    }
//
//    public void printNode(){
//        System.out.println("Node : " + this.data + "capacity: " + this.data.capacity);
//
//    }
//}
//
//public class BinaryTree {
//
//    Node root;
//
//    public void insert(Plane plane) {
//        Node node = new Node(plane);
//
//        /* set this node as the root if there is no root node yet */
//        if (root == null) {
//            root = node;
//        } else {
//            Node currentNode = root;
//            Node parent;
//            while (true) {
//                parent = currentNode;
//                if (plane.capacity < currentNode.data.capacity) {
//                    currentNode = currentNode.left;
//                    if (currentNode == null) {
//                        parent.left = node;
//                        return;
//                    }
//
//                }
//                else {
//                    currentNode = currentNode.right;
//                    if (currentNode == null) {
//                        parent.right = node;
//                        return;
//                    }
//                }
//            }
//        }
//    }
//
//    /** recursive function to call the which walks up the binary tree in order from left to right
//     *
//     * @param node - a Node instance
//     */
//    public void inOrderTraverse(Node node) {
//
//        if (node != null) {
//            inOrderTraverse(node.left);
//        }
//            inOrderTraverse(node.right);
//        }
//
//    }
//
//}
