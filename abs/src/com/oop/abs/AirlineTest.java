package com.oop.abs;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AirlineTest {

    private static Airport lhr;
    private static Airport sfo;

    static {
        try {
            /* create a new static Airport instances to use in the below tests */
            /* n.b - talking point real world implemention would use getter method to assign the variables as the
            result of a query for the airport name
            e.g. jfk = Airport.get("jfk") */
            lhr = new Airport("lhr");
            sfo = new Airport("sfo");
        } catch (NameValidationException | NonUniqueItemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAirlineCreate() throws NonUniqueItemException, NameValidationException{
        Airline airline = new Airline("joey");
        assertEquals( "joey", airline.name);
        Airline airline1 = new Airline("bob");
        assertEquals( "bob", airline1.name);
    }


    @Test
    void testBuildFlightMap() throws NameValidationException, NonUniqueItemException,
            NotFoundException, FlightInvalidException {
        Airline airline = new Airline("king");
        Airline airline1 = new Airline("queen");
        /* create two flight from lhr to sfo on the same day */
        Flight flight = new Flight(airline, lhr, sfo, new Date());
        Flight flight1 = new Flight(airline, lhr, sfo, new Date());

        /* build a key to query */
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String key = flight.source.name + flight.dest.name + df.format(flight.date);

        /* assert both flights added to the flightMap hashmap at the right key location */
        assertEquals(Airline.flightMap.get(key).size(), 2);

        /* add another Flight from lhr to sfo but this time with a different Airline */
        Flight flight2 = new Flight(airline1, lhr, sfo, new Date());

        /* assert that as the fligthMap is static all flights from both airlines are present */
        assertEquals(Airline.flightMap.get(key).size(), 3);
        LinkedList<Flight> flights = Airline.flightMap.get(key);

        /* assert that the linked list contains pointers to the Flight object */
        assert flights.get(0) == flight;
        assert flights.get(1) == flight1;
        assert flights.get(2) == flight2;
    }

    /* Exceptions */

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
        assertEquals(exception.getClass().toString(), "class com.oop.abs.NameValidationException");

    }

    @Test
    void testThrowsNonUniqueItemException() throws NameValidationException, NonUniqueItemException {
        /**
         Fancy Lambda syntax - the function is being passed into the assertThrows call with the -> operator.

         See: https://junit.org/junit5/docs/current/user-guide/#extensions-exception-handling
         **/
        Airline airline = new Airline("JOE");
        Throwable exception = assertThrows(NonUniqueItemException.class,
                ()-> {new Airline("JOE");
                });
        assertEquals("Airline with name JOE already exists", exception.getMessage());
        assertEquals(exception.getClass().toString(), "class com.oop.abs.NonUniqueItemException");
    }

}


