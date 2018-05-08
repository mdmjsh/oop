package com.oop.abs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlaneTest {
    @Test
    void testThrowsCapacityValidationException(){
        Throwable exception = assertThrows(NameValidationException.class,
                () -> {
                    new Plane("too big", 3001);
                });
        assertEquals("Plane capacity must be between 1-3000 seats.", exception.getMessage());

        exception = assertThrows(NameValidationException.class,
                () -> {
                    new Plane("too small", 0);
                });
        assertEquals("Plane capacity must be between 1-3000 seats.", exception.getMessage());
    }

    @Test
    void testToggleAvailability() throws CapacityValidationException {
        Plane plane = new Plane("blah", 300);
        assertEquals(plane.available, true);
        plane.toggleAvailabity();
        assertEquals(plane.available, false);

    }

    @Test
    void testCompareTo()throws CapacityValidationException{
        Plane plane = new Plane("blah", 300);
        Plane plane1 = new Plane("blah", 300);
        Plane plane2 = new Plane("blah", 30);
        assertEquals(plane.compareTo(plane1), 0);
        assertEquals(plane.compareTo(plane2), 1);
        assertEquals(plane2.compareTo(plane), -1);
    }


}