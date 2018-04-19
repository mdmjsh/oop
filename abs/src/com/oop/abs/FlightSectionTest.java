package com.oop.abs;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FlightSectionTest {

        private static Airline airline;

    static {
        try {
            airline = new Airline("air");
        } catch (NameValidationException e) {
            e.printStackTrace();
        } catch (NonUniqueItemException e) {
            e.printStackTrace();
        }
    }

    private static Flight flight;

    static {
        try {
            flight = new Flight(airline, "london", "mexico city");
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (FlightInvalidException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testFlightSectionCreate() throws FlightSectionValidationException {
        int rows = 99;
        int cols = 9;
        LinkedList<FlightSection> flightSections = new LinkedList<>();
        /** n.b. using an array of FlightSection[3] would also work, but the boundless linkedlist is more future proof.
         * e.g. if further seat classes are added in the future, this test will not break.
         **/

        /** iterate the enum and add a FlightSection instance to the flightSections linked list
         * assert that the correct FlightSections are added each time and that the ll is the correct size.
         * **/
        int i = 0;
        for (FlightSection.SeatClass sc : FlightSection.SeatClass.values()) {
            flightSections.add(new FlightSection(rows, cols, sc));
            FlightSection fs = flightSections.get(i);
            /** assert that the correct seatClass and rows/cols have been created **/
            assert fs.columns == cols;
            assert fs.rows == rows;
            assert fs.seatClass == sc;
            /** assert that the correct number of items have been added to the linked list **/
            assert flightSections.size() == i+1;
            i ++;
        }
    }

    @Test
    public void testThrowsFlightSectionValidationException() {
        /** Test permutations of flight section exceptions **/

        Throwable exception = assertThrows(FlightSectionValidationException.class,
                /** too many rows **/
                ()-> {FlightSection errorSection = new FlightSection(101, 10, FlightSection.SeatClass.FIRST);
                });
        assertEquals("Flight section must have at least one seat, and at most 100 rows and 10 columns",
                exception.getMessage());

            /** too many columns **/
        Throwable exception1 = assertThrows(FlightSectionValidationException.class,
                ()-> {FlightSection errorSection1 = new FlightSection(100, 11, FlightSection.SeatClass.FIRST);
                });
        assertEquals("Flight section must have at least one seat, and at most 100 rows and 10 columns",
                exception.getMessage());

            /** 0 rows **/
        Throwable exception2 = assertThrows(FlightSectionValidationException.class,
                ()-> {FlightSection errorSection1 = new FlightSection(0, 11, FlightSection.SeatClass.FIRST);
                });
        assertEquals("Flight section must have at least one seat, and at most 100 rows and 10 columns",
                exception.getMessage());

        /** 0 columns **/
        Throwable exception3 = assertThrows(FlightSectionValidationException.class,
                ()-> {FlightSection errorSection1 = new FlightSection(100, 0, FlightSection.SeatClass.FIRST);
                });
        assertEquals("Flight section must have at least one seat, and at most 100 rows and 10 columns",
                exception.getMessage());

    }
    @Test
    public void testHasAvailableSeats() throws FlightSectionValidationException, NonUniqueItemException,
            NotFoundException, FlightInvalidException, SeatClassFullException {
        FlightSection first = new FlightSection(1, 1, FlightSection.SeatClass.FIRST);
        Flight flight = new Flight(airline, "london", "paris");
        flight.addFlightSection(first);
        assertEquals(first.hasAvailableSeats(), true);
        first.bookSeat();
//        assertEquals(first.hasAvailableSeats(), false);
        System.out.println(first.seats.getLast().booked);
    }
}

