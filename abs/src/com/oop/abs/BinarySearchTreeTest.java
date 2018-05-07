package com.oop.abs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        /* test searchCapacity function */
        Plane found = tree.searchCapacity(tree.bst, 1);
        assertEquals(found, one);
        found = tree.searchCapacity(tree.bst, 6);
        assertEquals(found, six);
        found = tree.searchCapacity(tree.bst, 7);
        assertEquals(found, seven);

        /* make 'six' unavailable and assert it doesn't return and throws an exception instead */
        six.toggleAvailabity(); // false

        Throwable exception = assertThrows(NotFoundException.class, () -> { tree.searchCapacity(tree.bst, 6);
        });
        assertEquals("No Plane with capacity: 6 found",
                exception.getMessage());

        /* make 'six' available again and retest search */
        six.toggleAvailabity(); // true
        found = tree.searchCapacity(tree.bst, 6);
        assertEquals(found, six);

        /* assert raises exception */

        Throwable exception1 = assertThrows(NotFoundException.class, () -> {
            tree.searchCapacity(tree.bst, 8);
        });
        assertEquals("No Plane with capacity: 8 found",
                exception1.getMessage());
        assertEquals(exception1.getClass().toString(), "class com.oop.abs.NotFoundException");
    }

}