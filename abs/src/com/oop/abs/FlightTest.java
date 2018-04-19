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
    void testCreateFlight() throws NotFoundException, FlightInvalidException {
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
            FlightInvalidException, NotFoundException {
        FlightSection fs = new FlightSection(99, 10, FlightSection.SeatClass.BUSINESS);
        Flight flight = new Flight(airline, "london", "mexico city");
        flight.addFlightSection(fs);
        /** assert that a reference to the flight section instance has been created in the flightSections array **/
        assertEquals(flight.flightSections.indexOf(fs), 0);
    }

    @Test
    void testNonUniqueItemExceptionThrown() throws NonUniqueItemException, FlightSectionValidationException,
            FlightInvalidException, NotFoundException {
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

    @Test
    public void testGenerateSeats() throws FlightSectionValidationException, NonUniqueItemException,
            NotFoundException, FlightInvalidException {
        /** test that generating seats from a FlightSection instance
         * generates an array of correctly proportioned seats **/

        /** generate a flight section with 5*5 dimensions starting from seat A1 and ending at seat E25 **/
        int rows = 5;
        int columns = 5;
        Flight flight = new Flight(airline, "london", "san francisco");
        FlightSection first = new FlightSection(rows, columns, FlightSection.SeatClass.FIRST);
        flight.addFlightSection(first);
        assertEquals(flight.seats.getFirst().id, "A1");
        assertEquals(flight.seats.getLast().id, "E25");
        assertEquals(flight.seats.size(), rows * columns);
        /** assert that the flight and the flightSection 'seats' ll is the same object in the heap **/
        assertEquals(flight.seats, first.seats);

        /** generate a flight section with 10*10 dimensions starting from seat A26 and ending at seat J125 **/
        rows = 10;
        columns = 10;
        FlightSection business = new FlightSection(rows, columns, FlightSection.SeatClass.BUSINESS);
        flight.addFlightSection(business);
        assertEquals(flight.seats.getLast().id, "J125");
    }
}