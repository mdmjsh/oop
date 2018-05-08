package com.oop.abs;

import org.junit.jupiter.api.Test;

class BinarySearchTreeTest {

    @Test
    void testBSTOperations() throws CapacityValidationException, NotFoundException {
        SystemManager sm = new SystemManager();
        Plane one = sm.createPlane("one", 1);
        sm.createPlane("two", 2);
        sm.createPlane("three", 3);
        sm.createPlane("four", 4);
        sm.createPlane("five", 5);
        Plane six = sm.createPlane("six", 6);
        Plane seven = sm.createPlane("seven", 7);
//        sm.displaySystemDetails();
        BalancedBinaryTree tree = new BalancedBinaryTree(sm.planes);
        System.out.println("Inorder traversal:");
        tree.inorder(tree.bst);

//        /* test searchCapacity function */
//        Plane found = tree.searchCapacity(tree.bst, 1);
//        assertEquals(found, one);
//        found = tree.searchCapacity(tree.bst, 6);
//        assertEquals(found, six);
//        found = tree.searchCapacity(tree.bst, 7);
//        assertEquals(found, seven);
//
//        /* make 'six' unavailable and assert it returns null */
//        six.toggleAvailabity(); // false
//        assertEquals(found, null);
//
//        /* make 'six' available again and retest search */
//        six.toggleAvailabity(); // true
//        found = tree.searchCapacity(tree.bst, 6);
//        assertEquals(found, six);
//
//        /* assert raises exception */
//
//        found = tree.searchCapacity(tree.bst, 8);
//        assertEquals(found, null);


    }

}