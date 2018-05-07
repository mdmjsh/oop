package com.oop.abs;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FlightTest {

    private static Airline airline;
    private static Airport lhr;
    private static Airport sfo;
    private static Airport jfk;

    static {
        try {
            /* create a new static airline instance to use in the below tests */
            airline = new Airline("air");
        } catch (NameValidationException | NonUniqueItemException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            /* create a new static Airport instances to use in the below tests */
            /* n.b - talking point real world implemention would use getter method to assign the variables as the 
            result of a query for the airport name 
            e.g. jfk = Airport.get("jfk") */
            lhr = new Airport("lhr");
            sfo = new Airport("sfo");
            jfk = new Airport("jfk");
        } catch (NameValidationException | NonUniqueItemException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testCreateFlight() throws NotFoundException, FlightInvalidException,
            NameValidationException, NonUniqueItemException {
        Flight flight = new Flight(airline, lhr, jfk, new Date());
        /* assert that the flight instance has been created with an reference to the airline instance */
        assertEquals(flight.airline, airline);
        /* assert that the airline.flight variable contains a reference to the flight instance */
        assert airline.flights.indexOf(flight) != -1;
    }
//

    @Test
    void testAddFlightSection() throws NonUniqueItemException, FlightSectionValidationException,
            FlightInvalidException, NotFoundException, NameValidationException {
        FlightSection fs = new FlightSection(99, 10, FlightSection.SeatClass.BUSINESS);
        Flight flight = new Flight(airline, jfk, sfo, new Date());
        flight.addFlightSection(fs);
        /* assert that a reference to the flight section instance has been created in the flightSections array */
        assertEquals(flight.flightSections.indexOf(fs), 0);
    }

    @Test
    void testHasAvailableSeats() throws NotFoundException, FlightInvalidException,
            FlightSectionValidationException, NonUniqueItemException, SeatBookedException, NameValidationException {
        Flight flight = new Flight(airline, sfo, lhr, new Date());
        FlightSection first = new FlightSection(1, 1, FlightSection.SeatClass.FIRST);
        FlightSection business = new FlightSection(1, 1, FlightSection.SeatClass.BUSINESS);

        flight.addFlightSection(business);
        flight.addFlightSection(first);

        /* Seats are available */
        assertEquals(flight.hasAvailableSeats(), true);

        /* book the only seat in business class */
        business.bookSeat("A1");

        /* Seats are available */
        assertEquals(flight.hasAvailableSeats(), true);
        assertEquals(business.seats.getLast().booked, true);

        /* book the only seat in first class */
        first.bookSeat("A2");

        /* no Seats are available */
        assertEquals(flight.hasAvailableSeats(), false);
        assertEquals(business.seats.getLast().booked, true);
    }


    /* EXCEPTIONS */
    @Test
    void testNotFoundExceptionThrown() throws NotFoundException, FlightInvalidException {
        /* remove the airline object */
        Airline.airlines.remove(0);
        /* send a client request to add a flight on the removed airline - this will error */
        Throwable exception = assertThrows(NotFoundException.class,
                () -> { new Flight(airline, lhr, jfk, new Date()); });
        assertEquals("Airline with name air not found",
                exception.getMessage());
        assertEquals(exception.getClass().toString(), "class com.oop.abs.NotFoundException");
    }

    @Test
    void testFlightInvalidExceptionThrown() throws NameValidationException, NonUniqueItemException {
        Airport airport = new Airport("JWG");

        Throwable exception = assertThrows(FlightInvalidException.class,
                () -> {
                    new Flight(airline, airport, airport, new Date());
                });
        assertEquals("Source and Destination of flight cannot be the same", exception.getMessage());
        assertEquals(exception.getClass().toString(), "class com.oop.abs.FlightInvalidException");
    }

    @Test
    void testNonUniqueItemExceptionThrown() throws NonUniqueItemException, FlightSectionValidationException,
            FlightInvalidException, NotFoundException, NameValidationException {
        /* test that only one instance of given seatClass can be added to a flight */
        FlightSection fs = new FlightSection(99, 10, FlightSection.SeatClass.BUSINESS);
        FlightSection fs1 = new FlightSection(99, 10, FlightSection.SeatClass.BUSINESS);
        Flight flight = new Flight(airline, jfk, lhr, new Date());
        flight.addFlightSection(fs);
        Throwable exception = assertThrows(NonUniqueItemException.class,
                () -> {
                    flight.addFlightSection(fs1);
                });
        assertEquals("Flight already contains a flight section with seat class BUSINESS",
                exception.getMessage());
        assertEquals(exception.getClass().toString(), "class com.oop.abs.NonUniqueItemException");
    }

}

