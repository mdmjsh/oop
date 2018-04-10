package com.oop.abs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AirportTest {

    @Test
    public void testAirportCreate(){
        Airport airport = null;
        try {
            airport = new Airport("JWH");
        } catch (NameValidationException e) {
            e.printStackTrace();
        } catch (NonUniqueItemException e) {
            e.printStackTrace();
        }
        System.out.println("created Airport: " + airport.name);
        }

    @Test
    public void testThrowsNameValidationException(){
        /**
        Fancy Lambda syntax - the function is being passed into the assertThrows call with the -> operator.

         See: https://junit.org/junit5/docs/current/user-guide/#extensions-exception-handling
         **/
        Throwable exception = assertThrows(NameValidationException.class,
                ()-> {Airport errorPort = new Airport("This name is too long!");} );
        assertEquals("Airport name must be exactly three characters", exception.getMessage());
        Throwable exception1 = assertThrows(NameValidationException.class,
                ()-> {Airport errorPort = new Airport("123");} );
        assertEquals("Airport name must be exactly three characters", exception.getMessage());


    }

    @Test
    public void testThrowsNonUniqueItemException() throws NameValidationException, NonUniqueItemException {
        /**
         Fancy Lambda syntax - the function is being passed into the assertThrows call with the -> operator.

         See: https://junit.org/junit5/docs/current/user-guide/#extensions-exception-handling
         **/
        Airport airport = new Airport("oop");

        System.out.println(airport.airports);
        System.out.println(airport.airports.indexOf("oop"));
        Throwable exception = assertThrows(NonUniqueItemException.class,
                ()-> {Airport errorPort = new Airport("oop");
                      Airport errorPort1 = new Airport("oop");
                });
        System.out.println(airport.airports);
        assertEquals("Airport name must be exactly three characters", exception.getMessage());
    }

}


