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
     void testGenerateSeats() throws FlightSectionValidationException, NonUniqueItemException,
            NotFoundException, FlightInvalidException, NameValidationException {
        /* test that generating seats from a FlightSection instance
          generates an array of correctly proportioned seats */

        /* generate a flight section with 5*5 dimensions starting from seat A1 and ending at seat E25 */
        int rows = 5;
        int columns = 5;
        Flight flight = new Flight(airline, lhr, jfk, new Date());
        FlightSection first = new FlightSection(rows, columns, FlightSection.SeatClass.FIRST);
        flight.addFlightSection(first);
        assertEquals(flight.seats.getFirst().id, "A1");
        assertEquals(flight.seats.getLast().id, "E5");
        assertEquals(flight.seats.size(), rows * columns);
        /* assert that the flight and the flightSection 'seats' ll is the same object in the heap */
        assertEquals(flight.seats, first.seats);

        /* generate another seat section and ensure that the seat numbering is continuous
        * n.b. the last seat added in the first class section is E5 therefore the first seat added will be F1.
        * */
        System.out.println("Last row added: " + flight.seats.getLast().row);
        rows = 10;
        columns = 10;
        FlightSection business = new FlightSection(rows, columns, FlightSection.SeatClass.BUSINESS);
        flight.addFlightSection(business);

        /* F1 + 100 seats == J15 * - uncomment Flight.161 for print demonstration */
        assertEquals(flight.seats.getLast().id, "J15");
    }

    @Test
    void testHasAvailableSeats() throws NotFoundException, FlightInvalidException,
            FlightSectionValidationException, NonUniqueItemException, SeatBookedException, NameValidationException {
        int rows = 1;
        int columns = 1;
        Flight flight = new Flight(airline, sfo, lhr, new Date());
        FlightSection first = new FlightSection(rows, columns, FlightSection.SeatClass.FIRST);
        FlightSection business = new FlightSection(rows, columns, FlightSection.SeatClass.BUSINESS);

        flight.addFlightSection(first);
        flight.addFlightSection(business);

        /* Seats are available */
        assertEquals(flight.hasAvailableSeats(), true);

        /* book the only seat in business class */
        business.bookSeat("A1");

        /* Seats are available */
        assertEquals(flight.hasAvailableSeats(), true);

        /* book the only seat in first class */
        first.bookSeat("A2");

        /* no Seats are available */
        assertEquals(flight.hasAvailableSeats(), false);

        /* add an economy section and retest */
        FlightSection eco = new FlightSection(rows, columns, FlightSection.SeatClass.ECONOMY);
        flight.addFlightSection(eco);

        assertEquals(flight.hasAvailableSeats(), true);
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
