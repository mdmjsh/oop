package com.oop.abs;

import java.util.LinkedList;

/**
 * A flight section has a seatClass (first, business or economy)
 * and must have at least 1 seat.
 *
 * A flight section can contain at most 100 rows of seats
 * and at most 10 columns of seats.
 *
 * hasAvailableSeats() returns true iff the section has some seats that are not booked,
 * and bookSeat() books an available seat.
 */

public class FlightSection {
    /**
     * enum data type is used for known predefined-constants
     * https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
     **/
    public enum SeatClass {
        FIRST, BUSINESS, ECONOMY
    }

    public SeatClass seatClass;
    public int rows;
    public int columns;
    private static int MAXROWS = 100;
    private static int MAXCOLS = 10;
    protected Flight flight;
    public LinkedList<Seat> seats = new LinkedList<>();

    /**
     * we need to create an linkedlist of the dimensions of the FlightSection to dynamically fill it with Seat objects
     * See: https://stackoverflow.com/questions/19105401/how-would-i-create-a-new-object-from-a-class-using-a-for-loop-in-java
     */
    public FlightSection(int rows, int columns, SeatClass seatClass) throws FlightSectionValidationException {
        this.seatClass = seatClass;
        this.rows = validateFlightSection(rows, MAXROWS);
        this.columns = validateFlightSection(columns, MAXCOLS);
    }

    private int validateFlightSection(int input, int limit) throws FlightSectionValidationException {
        if (input == 0 || input > limit) {
            throw new FlightSectionValidationException("Flight section must have at least one seat, and at most" +
                    " 100 rows and 10 columns");
        }
        return input;
    }

    public boolean hasAvailableSeats() {
        /* returns true iff the section has some seats that are not booked */
        for (Seat seat : this.seats) {
            if (!seat.booked) {
                return true;
            }
        }
        return false;
    }

    /** Books the seat at the specified id iff available **/
        public Seat bookSeat(String id) throws NotFoundException, SeatBookedException {
        if (hasAvailableSeats()) {
            if (getSeatById(id) == null){
                throw new NotFoundException("Seat with name " + id + " not found");
            }
            Seat seat = getSeatById(id);
            if (!seat.booked) {
                seat.booked = true;
                return seat;
            }
        }
        /* If we've got this far the seat has already been booked */
        throw new SeatBookedException(id, this.flight);
    }

    /**
     * n.b. possibly better encapsulated in the seat class
     **/
    public Seat getSeatById(String id) {
        /* return a seat by the id or throw NotFoundException */
        for (Seat seat : this.seats) {
            if (seat.id.equals(id)) {
                return seat;
            }
        }
        /* n.b. return null here rather than raise exception as we need to use this method in loop in the SystemManger
        to book a seat. Returning Null here means that we don't need to rely on exception handling for control flow -
        which is considered back practice in Java.
         */
        return null;
    }
}
