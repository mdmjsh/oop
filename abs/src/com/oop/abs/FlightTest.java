package com.oop.abs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FlightTest {

    private static Airline airline;

    static {
        try {
            /** create a new static airline instance to use in the below tests **/
            airline = new Airline("air");
        } catch (NameValidationException e) {
            e.printStackTrace();
        } catch (NonUniqueItemException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateFlight() throws NotFoundException, FlightInvalidException{
        Flight flight = new Flight(airline, "london", "san francisco");
        /** assert that the flight instance has been created with an reference to the airline instance **/
        assertEquals(flight.airline, airline);
        /** assert that the airline.flight variable contains a reference to the flight instance **/
        assert airline.flights.indexOf(flight) != -1;
    }

    @Test
    void testFlightInvalidExceptionThrown() {
        Throwable exception = assertThrows(FlightInvalidException.class,
                () -> {
                    new Flight(airline, "london", "london");
                });
        assertEquals("Source and Destination of flight cannot be the same", exception.getMessage());

        /** same test but with case sensitivity **/
        Throwable exception1 = assertThrows(FlightInvalidException.class,
                () -> {
                    new Flight(airline, "loNDon", "LoNdOn");
                });
        assertEquals("Source and Destination of flight cannot be the same", exception1.getMessage());
    }

    @Test
    void testAddFlightSection() throws NonUniqueItemException, FlightSectionValidationException,
            FlightInvalidException, NotFoundException{
        FlightSection fs = new FlightSection(99, 10, FlightSection.SeatClass.BUSINESS);
        Flight flight = new Flight(airline, "london", "mexico city");
        flight.addFlightSection(fs);
        /** assert that a reference to the flight section instance has been created in the flightSections array **/
        assertEquals(flight.flightSections.indexOf(fs), 0);
    }

    @Test
    void testNonUniqueItemExceptionThrown() throws NonUniqueItemException, FlightSectionValidationException,
            FlightInvalidException, NotFoundException{
        /** test that only one instance of given seatClass can be added to a flight **/
        FlightSection fs = new FlightSection(99, 10, FlightSection.SeatClass.BUSINESS);
        FlightSection fs1 = new FlightSection(99, 10, FlightSection.SeatClass.BUSINESS);
        Flight flight = new Flight(airline, "london", "mexico city");
        flight.addFlightSection(fs);
        Throwable exception = assertThrows(NonUniqueItemException.class,
                () -> {
                    flight.addFlightSection(fs1);
                });
        assertEquals("Flight already contains a flight section with seat classBUSINESS",
                exception.getMessage());
    }



}