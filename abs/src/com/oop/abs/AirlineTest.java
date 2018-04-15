package com.oop.abs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AirlineTest {

    @Test
    public void testAirlineCreate() throws NonUniqueItemException, NameValidationException{
        Airline airline = new Airline("joey");
        assertEquals( "joey", airline.name);
        Airline airline1 = new Airline("bob");
        assertEquals( "bob", airline1.name);
    }

    @Test
    public void testThrowsNameValidationException() {
        /**
         Fancy Lambda syntax - the function is being passed into the assertThrows call with the -> operator.

         See: https://junit.org/junit5/docs/current/user-guide/#extensions-exception-handling
         **/
        Throwable exception = assertThrows(NameValidationException.class,
                () -> {
                    Airline errorLine = new Airline("This name is too long!");
                });
        assertEquals("Airline name must be less than six characters", exception.getMessage());


        Throwable exception1 = assertThrows(NameValidationException.class,
                () -> {
                    Airline errorLine = new Airline("123");
                });
        assertEquals("Airline name must contain only Alphabetic characters", exception1.getMessage());


        Throwable exception2 = assertThrows(NameValidationException.class,
                () -> {
                    Airline errorLine = new Airline("");
                });
        assertEquals("Airline name cannot be empty string!", exception2.getMessage());

    }

    @Test
    public void testThrowsNonUniqueItemException() throws NameValidationException, NonUniqueItemException {
        /**
         Fancy Lambda syntax - the function is being passed into the assertThrows call with the -> operator.

         See: https://junit.org/junit5/docs/current/user-guide/#extensions-exception-handling
         **/
        Airline airline = new Airline("JFK");
        Throwable exception = assertThrows(NonUniqueItemException.class,
                ()-> {new Airline("JFK");
                });
        assertEquals("Airline with name JFK already exists", exception.getMessage());
    }

}


