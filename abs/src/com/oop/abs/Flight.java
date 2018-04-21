package com.oop.abs;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

/**
 * A flight can be associated with 0 or more flight sections.
 *
 * There can only be one flight section of a particular seat class in a flight
 * All flights for a given airline must have unique flight ids.
 * A flight has an originating airport and a destination airport.
 * The originating and destination airports cannot be the same.
 *
 **/
public class Flight {
    public UUID id;
    public Airline airline;
    /* n.b. discussion_point - could have used FlightSection.SeatClass.values().length to make an array of length of flight sectionEnums **/
    public LinkedList <FlightSection> flightSections = new LinkedList<>();
    public String source;
    public String dest;
    public Date date;
    public LinkedList<Seat> seats = new LinkedList<>();

    /* Associate a flight to an airline or raise an exception **/
        public Flight(Airline airline, String source, String dest, Date date) throws NotFoundException,
                FlightInvalidException{
            if (Airline.find(airline.name) == null){
                throw new NotFoundException("Airline", airline.name);
            }
            this.airline = airline;

            /* possible refactor - this is not 100% guaranteed for uniqueness */
            this.id = UUID.randomUUID();

            if (!validFlight(source, dest)){
                throw new FlightInvalidException("Source and Destination of flight cannot be the same");
            }
            this.source = source;
            this.dest = dest;
            /* n.b. should dates be validated that they are not in the past? business decision. I think no. */
            this.date = date;

            /* after we've validated all flight info, add the flight to the airline's flights variable **/
            this.airline.flights.add(this);
            this.airline.buildFlightMap(this);


    }

    private static boolean validFlight(String source, String dest){
    /* convert both string to lowercase and check the source and destination are distinct **/
        return !source.toLowerCase().equals(dest.toLowerCase());
    }


    /**
     * Associates a Flight instance with a FlightSection instance.
     * only one FlightSection of each SeatClass is allow on a Flight.
     *
     * @param: flightSection - a FlightSection instance
     */
    public void addFlightSection(FlightSection flightSection) throws NonUniqueItemException{

        if (existingFlightSection(flightSection)){
            throw new NonUniqueItemException("Flight already contains a flight section with seat class" +
                    flightSection.seatClass);
        }
        this.flightSections.add(flightSection);

        /*
          also associate the Flight instance with the flight attribute of the flightSections item added

          i.e the Flight knows about its FlightSections, and the FlightSection knows about its parent Flight instance.
          */
        this.flightSections.getLast().flight = this;
        generateSeats(flightSection);
    }


    /**
     * iterate the flight sections linked list
     * and check that the seatClass of the input flightSection hasn't already been added
     *
     * @param: flightSection - FlightSection instance
     * @return: int, index of next place in the array to be added or -1
     *
     * **/

    private boolean existingFlightSection(FlightSection flightSection){
        int i = 0;
        while (i < this.flightSections.size()) {
            if (this.flightSections.get(i).seatClass.equals(flightSection.seatClass)) {
                /* return straight away - don't finish the while loop */
                return true;
            }
            i ++;
        }
        return false;
    }

        /**
         * generate a seat instance for all of the seats by the given dimensions of a FlightSection instance and add
         * a pointer to its to the seats linkedlist.
         *
         * @param: flightSection - the instantiated flightSection object (this)
         */
        private void generateSeatsOld(FlightSection flightSection){

            int previousRow=1;
            try {
                /* get the last seat added in the flight */
                previousRow = this.seats.getLast().row;
            } catch (java.util.NoSuchElementException e) {
                /* if no seat has been added at all yet start at row 1, column 1 */
                Seat seat = new Seat (1, 1, flightSection.seatClass);
                this.seats.add(seat);
//                this.seats.add(new Seat(1, 1, flightSection.seatClass));
            }
            for (int column=0; column <= flightSection.columns-1; column++) {
                /* this enable us to build up the seating blocks evenly */
                System.out.println("Previous row: " + previousRow);
                for (int row = previousRow +1; row <= flightSection.rows; row++) {
                    Seat seat = new Seat (column, row, flightSection.seatClass);
                    System.out.println(seat.id);
                    this.seats.add(seat);
//                    this.seats.add(new Seat(column, row, flightSection.seatClass));
                }
            }
            /* Flight section also has awareness of its seats */
            flightSection.seats = this.seats;
        }


    /**
     * Calculate the size of the size of the flight section and then iteratively add Seat objects to this.seats ll.
     *
     * If the number of iterations == the size of this.column, we've added all required seats in the current row and
     * need to move to the next row by incrementing the row number.
     *
     * Until fill all of the required seats in the FlightSection.
     *
     * @param flightSection
     */
    public void generateSeats(FlightSection flightSection){
            int size = flightSection.rows * flightSection.columns;
            int column =1;
            int row;

            try {
            /*  get the last seat added in the flight so we can continue from
                where the previous FlightSection left off */
                row = this.seats.getLast().row +1;
        } catch (java.util.NoSuchElementException e) {
                /* if we've not yet added any seats start from row 1 */
                row = 1;
            }
            for (int i=1; i<=size; i++){
                /* add the Seat object at the given row, column coordinates and move to the next column */

//                System.out.println("iteration " +i + " seat: " + seat.id);
                this.seats.add(new Seat(column, row, flightSection.seatClass));
                column ++;
                /* check if we've added the required number Seats for this row,
                    and if so move to the next row and reset the column back to 1 */
                if(column > flightSection.columns){
                    row ++;
                    column =1;
                }
            }
            /* Flight section also has awareness of its seats */
            flightSection.seats = this.seats;
    }
}
