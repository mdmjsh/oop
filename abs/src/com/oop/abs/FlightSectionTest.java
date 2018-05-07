package com.oop.abs;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FlightSectionTest {

    private static Airline airline;

    static {
        try {
            airline = new Airline("air");
        } catch (NameValidationException | NonUniqueItemException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testFlightSectionCreate() throws FlightSectionValidationException {
        int rows = 99;
        int cols = 9;
        LinkedList<FlightSection> flightSections = new LinkedList<>();
        /*
         * n.b. using an array of FlightSection[3] would also work, but the boundless linkedlist is more future proof.
         * e.g. if further seat classes are added in the future, this test will not break.
         */

        /* iterate the enum and add a FlightSection instance to the flightSections linked list
         * assert that the correct FlightSections are added each time and that the ll is the correct size.
         * */
        int i = 0;
        for (FlightSection.SeatClass sc : FlightSection.SeatClass.values()) {
            flightSections.add(new FlightSection(rows, cols, sc));
            FlightSection fs = flightSections.get(i);
            /* assert that the correct seatClass and rows/cols have been created */
            assert fs.columns == cols;
            assert fs.rows == rows;
            assert fs.seatClass == sc;
            /* assert that the correct number of items have been added to the linked list */
            assert flightSections.size() == i + 1;
            i++;
        }
    }

    @Test
    void testHasAvailableSeats() throws FlightSectionValidationException, NonUniqueItemException,
            NotFoundException, FlightInvalidException, SeatClassFullException, SeatBookedException, NameValidationException {
        /* add two seats to the flight section **/
        FlightSection first = new FlightSection(1, 2, FlightSection.SeatClass.FIRST);
        Flight flight = new Flight(airline, new Airport("PRI"), new Airport("GWW"), new Date());
        flight.addFlightSection(first);
        /* check there are seats available after adding them **/
        assertEquals(first.hasAvailableSeats(), true);

        /* book a seat **/
        first.bookSeat("A1");
        /* assert still available seats **/
        assertEquals(first.hasAvailableSeats(), true);

        /* book another seat **/
        first.bookSeat("B1");
        /* no seats remaining in the flight section **/
        assertEquals(first.hasAvailableSeats(), false);
    }

    @Test
    void testBookSeat() throws FlightSectionValidationException, NotFoundException, FlightInvalidException,
            NonUniqueItemException, SeatBookedException, NameValidationException {
        FlightSection first = new FlightSection(1, 2, FlightSection.SeatClass.FIRST);
        Flight flight = new Flight(airline, new Airport("LGW"), new Airport("MMR"), new Date());
        flight.addFlightSection(first);
        Seat A1 = first.getSeatById("A1");
        Seat B1 = first.getSeatById("B1");
        /* assert that Seat A1 is not already book */
        assertEquals(A1.booked, false);
        /* book Seat A1 and assert that is is booked after the transaction is complete */
        Seat bookedSeat = first.bookSeat("A1");
        assertEquals(bookedSeat.booked, true);

        /* assert that the action seat booking was atomic to A1 */
        assertEquals(B1.booked, false);

        /* assert that A1 and the bookedSeat are pointers to the same object */
        assertEquals(bookedSeat, A1);
    }
//
    @Test
    void testGetSeatByID() throws NonUniqueItemException, FlightSectionValidationException, NotFoundException,
            FlightInvalidException, SeatBookedException, NameValidationException {
        FlightSection first = new FlightSection(1, 2, FlightSection.SeatClass.FIRST);
        FlightSection business = new FlightSection(1, 2, FlightSection.SeatClass.BUSINESS);
        Flight flight = new Flight(airline, new Airport("LHR"), new Airport("GBK"), new Date());
        flight.addFlightSection(first);
        flight.addFlightSection(business);

        Seat A1 = first.getSeatById("A1");

        /* assert that the correct ID has been matched */
        assertEquals(A1.id, "A1");

        /* assert that the seat returned is the actual seat object in the flight */
        assertEquals(A1, flight.flightSections.getFirst().seats.getFirst());
        }

    @Test
    void testGenerateSeats() throws FlightSectionValidationException, NonUniqueItemException,
            NotFoundException, FlightInvalidException, NameValidationException {
        /* test that generating seats from a FlightSection instance
          generates an array of correctly proportioned seats */

        /* generate a flight section with 5 * 5 dimensions starting from seat A1 and ending at seat E25 */
        int rows = 5;
        int columns = 5;
        System.out.println("FIRST CLASS:");

        Flight flight = new Flight(airline, new Airport("LGW"), new Airport("MMR"), new Date());
        FlightSection first = new FlightSection(5, 5, FlightSection.SeatClass.FIRST);
        flight.addFlightSection(first);

        assertEquals(first.seats.getFirst().id, "A1");
        assertEquals(first.seats.getLast().id, "E5");
        /* assert that the correct number of Seats have been generated and added to the seats LinkedList */
        assertEquals(first.seats.size(), rows * columns);
        /* assert that the flight and the flightSection 'seats' ll is the same object in the heap */
        assertEquals(first.seats, first.seats);

        /* generate another seat section and ensure that the seat numbering is continuous
         * n.b. the last seat added in the first class section is E5 therefore the first seat added will be F1.
         * */
        System.out.println("BUSINESS CLASS:");
        FlightSection business = new FlightSection(rows, columns, FlightSection.SeatClass.BUSINESS);
        flight.addFlightSection(business);

        assertEquals(business.seats.getLast().id, "E10");


        System.out.println("FIRST CLASS:");
        FlightSection eco = new FlightSection(rows, columns, FlightSection.SeatClass.ECONOMY);
        flight.addFlightSection(eco);
        assertEquals(eco.seats.getLast().id, "E15");

    }

    /* Exceptions */

    @Test
    void testThrowsSeatBookedException() throws NonUniqueItemException, NotFoundException, FlightInvalidException,
            FlightSectionValidationException, SeatBookedException, NameValidationException {
        FlightSection first = new FlightSection(1, 2, FlightSection.SeatClass.FIRST);
        Flight flight = new Flight(airline, new Airport("HKK"), new Airport("SFO"), new Date());
        flight.addFlightSection(first);
        first.bookSeat("A1");

        Throwable exception = assertThrows(SeatBookedException.class, () -> {
            first.bookSeat("A1");
        });
        assertEquals("Seat: A1 on flight: " + flight.id + " already booked", exception.getMessage());
        assertEquals(exception.getClass().toString(), "class com.oop.abs.SeatBookedException");
    }

    @Test
    void testThrowsFlightSectionValidationException() {
        /* Test permutations of flight section exceptions **/

        Throwable exception = assertThrows(FlightSectionValidationException.class,
                /* too many rows **/
                () -> {
                    new FlightSection(101, 10, FlightSection.SeatClass.FIRST);
                });
        assertEquals("Flight section must have at least one seat, and at most 100 rows and 10 columns",
                exception.getMessage());


        /* too many columns **/
        Throwable exception1 = assertThrows(FlightSectionValidationException.class, () -> {
            new FlightSection(100, 11, FlightSection.SeatClass.FIRST);
        });
        assertEquals("Flight section must have at least one seat, and at most 100 rows and 10 columns",
                exception.getMessage());

        /* 0 rows **/
        Throwable exception2 = assertThrows(FlightSectionValidationException.class, () -> {
            new FlightSection(0, 11, FlightSection.SeatClass.FIRST);
        });
        assertEquals("Flight section must have at least one seat, and at most 100 rows and 10 columns",
                exception2.getMessage());

        /* 0 columns **/
        Throwable exception3 = assertThrows(FlightSectionValidationException.class, () -> {
            new FlightSection(100, 0, FlightSection.SeatClass.FIRST);
        });
        assertEquals("Flight section must have at least one seat, and at most 100 rows and 10 columns",
                exception.getMessage());
        assertEquals(exception3.getClass().toString(), "class com.oop.abs.FlightSectionValidationException");

    }

    @Test
    void testNotFoundExceptionThrown() throws FlightSectionValidationException, NotFoundException,
             NonUniqueItemException, NameValidationException, FlightInvalidException {
        FlightSection first = new FlightSection(1, 2, FlightSection.SeatClass.FIRST);
        Flight flight = new Flight(airline, new Airport("JJH"), new Airport("GHD"), new Date());
        flight.addFlightSection(first);
        Throwable exception = assertThrows(NotFoundException.class, () -> { first.bookSeat("A3");});
        assertEquals("Seat with name A3 not found",
                exception.getMessage());
        assertEquals(exception.getClass().toString(), "class com.oop.abs.NotFoundException");
    }
}

