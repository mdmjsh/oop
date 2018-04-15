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
    public Seat seats[] = new Seat[rows * columns];
    /** we need to create an array of the dimensions of the FlightSection to dynamically fill it with Seat objects
     * See: https://stackoverflow.com/questions/19105401/how-would-i-create-a-new-object-from-a-class-using-a-for-loop-in-java
     */
    public Flight flight;
    private int nextSeat = 1;

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
        System.out.println(this.seats.length);
        for (int column=1; column<=this.columns; column++) {
            /** this.nextSeat is used as a mechanism to culmalitvely add numerical values
             * to the seats. i.e. the next seatClass row number should = the previous seatClass row number +1.
             */
            for (int row=this.nextSeat; row<=this.rows; row++) {
                /** add a Seat instance to the Seat arrray at the current index **/
                /** TO DO: FIX THIS BIT!! **/
                seats[row] = new Seat(column, row, this.seatClass);
//                System.out.println(this.seats[row-1].id);
                this.nextSeat ++;
            }
        }
    }
 }
