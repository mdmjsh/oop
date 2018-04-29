package com.oop.abs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AirportTest {

    @Test
    public void testAirportCreate()throws NonUniqueItemException, NameValidationException{
        Airport airport = new Airport("joe");
        assertEquals( "JOE", airport.name);
        Airport airport1 = new Airport("bob");
        assertEquals( "BOB", airport1.name);
    }

    @Test
    public void testThrowsNameValidationException() {
        /**
         Fancy Lambda syntax - the function is being passed into the assertThrows call with the -> operator.

         See: https://junit.org/junit5/docs/current/user-guide/#extensions-exception-handling
         **/
        Throwable exception = assertThrows(NameValidationException.class,
                () -> {
                    Airport errorPort = new Airport("This name is too long!");
                });
        assertEquals("Airport name must be exactly three characters", exception.getMessage());


        Throwable exception1 = assertThrows(NameValidationException.class,
                () -> {
                    Airport errorPort = new Airport("123");
                });
        assertEquals("Airport name must contain only Alphabetic characters", exception1.getMessage());
        assertEquals(exception.getClass().toString(), "class com.oop.abs.NameValidationException");
        }


    @Test
    public void testThrowsNonUniqueItemException() throws NameValidationException, NonUniqueItemException {
        /**
         Fancy Lambda syntax - the function is being passed into the assertThrows call with the -> operator.

         See: https://junit.org/junit5/docs/current/user-guide/#extensions-exception-handling
         **/
        Airport airport = new Airport("JFK");
        Throwable exception = assertThrows(NonUniqueItemException.class,
                ()-> {Airport errorPort = new Airport("JFK");
                });
        assertEquals("Airport with name JFK already exists", exception.getMessage());

        /* test for case sensitivity in duplicate airport names */
        Throwable exception1 = assertThrows(NonUniqueItemException.class,
                () -> {
                    Airport airport1 = new Airport("jFk");
                });
        assertEquals("Airport with name JFK already exists", exception1.getMessage());
        assertEquals(exception.getClass().toString(), "class com.oop.abs.NonUniqueItemException");
    }

}


