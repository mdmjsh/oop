package com.oop.abs;


/**  A flight section has a seat class (first, business or economy)
 *   and must have at least 1 seat.
 *
 *   A flight section can contain at most 100 rows of seats
 *   and at most 10 columns of seats.
 *
 *   hasAvailableSeats() returns true iff the section has some seats that are not booked,
 *   and bookSeat() books an available seat.
 */

public class FlightSection {
    /**
     * enum data type is used for known predefined-constants
     * https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
     * **/
    public enum  SeatClass {FIRST, BUSINESS, ECONOMY}
    public SeatClass seatClass;
    public int rows;
    public int columns;
    private static int MAXROWS = 100;
    private static int MAXCOLS= 10;

    public FlightSection(int rows, int cols, SeatClass sc) throws FlightSectionValidationException {
        this.seatClass = sc;
        this.rows = validateFlightSection(rows, MAXROWS);
        this.columns = validateFlightSection(cols, MAXCOLS);
    }

    private int validateFlightSection(int input, int limit) throws FlightSectionValidationException{
        if(input == 0 || input > limit) {
            throw new FlightSectionValidationException("Flight section must have at least one seat, and at most" +
                    " 100 rows and 10 columns");
        }
        return input;
    }
}
