//package com.oop.abs;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class BinarySearchTreeTest {
//
//    @Test
//    void testNodeInsertion() throws CapacityValidationException {
//        SystemManager sm = new SystemManager();
//        Plane one = sm.createPlane("one", 50);
//        Plane two = sm.createPlane("two", 500);
//        Plane three = sm.createPlane("three", 10);
//        Plane four = sm.createPlane("four", 80);
//
//        assertEquals(sm.planes.root.data, one);
//        assertEquals(sm.planes.root.left.data, three);
//
//        assertEquals(sm.planes.size, 4);
//        sm.createPlane("five", 86);
//        assertEquals(sm.planes.size, 5);
//    }
//}