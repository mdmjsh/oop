package com.oop.abs;

/**  A flight section has a seatClass (first, business or economy)
 *   and must have at least 1 seat.
 *
 *   A flight section can contain at most 100 rows of seats
 *   and at most 10 columns of seats.
 *
 *   hasAvailableSeats() returns true iff the section has some seats that are not booked,
 *   and bookSeat() books an available seat.
 *
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
    protected Flight flight;
    /** we need to create an linkedlist of the dimensions of the FlightSection to dynamically fill it with Seat objects
     * See: https://stackoverflow.com/questions/19105401/how-would-i-create-a-new-object-from-a-class-using-a-for-loop-in-java
     */

    public FlightSection(int rows, int cols, SeatClass seatClass) throws FlightSectionValidationException {
            this.seatClass = seatClass;
            this.rows = validateFlightSection(rows, MAXROWS);
            this.columns = validateFlightSection(cols, MAXCOLS);
            generateSeats(this);
        }

            private int validateFlightSection(int input, int limit) throws FlightSectionValidationException{
                if(input == 0 || input > limit) {
            throw new FlightSectionValidationException("Flight section must have at least one seat, and at most" +
                    " 100 rows and 10 columns");
        }
        return input;
    }

    public void generateSeats(FlightSection flightSection){
        /** generate a seat instance for all of the seats by the given dimensions of a FlightSection instance and add
         * a pointer to its to the seats linkedlist.
         *
         * @param: flightSection - the instantiated flightSection object (this)
         */
        int row = 0;
        for (int column=0; column <= this.columns-1; column++) {
//            System.out.println("column: " + column);
            for (int i=0; i <= this.rows-1; i++) {
//                System.out.println("row: " + row);
                try {
                    /** get the last seat added in the flight and increment **/
                    row = this.flight.seats.getLast().row + 1;
                } catch (java.util.NoSuchElementException e) {
                    row = 1;
                }
                this.flight.seats.add(new Seat(column, row, this.seatClass));
            }
        }
    }
 }
