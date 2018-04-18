package com.oop.abs;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FlightSectionTest {

        private static Airline airline = new Airline("air");
        private static Flight flight = new Flight(airline, "london", "mexico city");


    @Test
    public void testFlightSectionCreate() throws FlightSectionValidationException {
        int rows = 100;
        int cols = 10;
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
    public void testGenerateSeats() throws FlightSectionValidationException {
        /** test that generating seats from a FlightSection instance
         * generates an array of correctly proportioned seats **/

        /** generate a flight section with 5*5 dimensions starting from seat A1 and ending at seat E25 **/
        int rows = 5;
        int columns = 5;
        FlightSection first = new FlightSection(rows,columns, FlightSection.SeatClass.FIRST);
        flight.addFlightSection(first);
        assertEquals(first.flight.seats.getFirst().id, "A1");
        assertEquals(first.flight.seats.getLast().id, "E25");
        assertEquals(first.flight.seats.size(),  rows * columns);

        /** generate a flight section with 10*10 dimensions starting from seat A26 and ending at seat J126 **/
        rows = 10;
        columns = 10;
        FlightSection business = new FlightSection(rows,columns, FlightSection.SeatClass.BUSINESS);
//        assertEquals(business.seats.getFirst().id, "A26");
//        assertEquals(business.seats.getLast().id, "J126");
        assertEquals(business.flight.seats.size(),  rows * columns);


        System.out.println(business.flight.seats.getLast().id);



    }

}
