package com.oop.abs;

import java.util.LinkedList;
import java.util.UUID;

    /** A flight can be associated with 0 or more flight sections.
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
    /** n.b. disccusion_point - could have used FlightSection.SeatClass.values().length to make an array of length of flight sectionEnums **/
    public LinkedList <FlightSection> flightSections = new LinkedList<>();
    public String source;
    public String dest;
    public LinkedList<Seat> seats = new LinkedList<>();

    /** Associate a flight to an airline or raise an exception **/
        public Flight(Airline airline, String source, String dest) throws NotFoundException, FlightInvalidException{
            if (Airline.find(airline.name) == null){
                throw new NotFoundException("Airline", airline.name);
            }
            this.airline = airline;

            /** possible refactor - this is not 100% guaranteed for uniqueness */
            this.id = UUID.randomUUID();

            if (!validFlight(source, dest)){
                throw new FlightInvalidException("Source and Destination of flight cannot be the same");
            }
            this.source = source;
            this.dest = dest;

            /** after we've validated all flight info, add the flight to the airline's flights variable **/
            this.airline.flights.add(this);
    }

    private static boolean validFlight(String source, String dest){
    /** convert both string to lowercase and check the source and destination are distinct **/
        if (source.toLowerCase().equals(dest.toLowerCase())){
            return false;
        }
        return true;
    }

    public void addFlightSection(FlightSection flightSection) throws NonUniqueItemException{
        /** Associates a Flight instance with a FlightSection instance.
         * only one FlightSection of each SeatClass is allow on a Flight.
         *
         * @param: flightSection - a FlightSection instance
         */
        if (existingFlightSection(flightSection)){
            throw new NonUniqueItemException("Flight already contains a flight section with seat class" +
                    flightSection.seatClass);
        }
        this.flightSections.add(flightSection);

        /** also associate the Flight instance with the flight attribute of the flightSections item added
         *
         * i.e the Flight knows about its FlightSections, and the FlightSection knows about its parent Flight instance.
         * **/
        this.flightSections.getLast().flight = this;
    }


    private boolean existingFlightSection(FlightSection flightSection){
        /** iterate the flight sections linked list
         * and check that the seatClass of the input flightSection hasn't already been added
         *
         * @param: flightSection - FlightSection instance
         * @returns: int, index of next place in the array to be added or -1
         *
         * **/
        int i = 0;
        while (i < this.flightSections.size()) {
            if (this.flightSections.get(i).seatClass.equals(flightSection.seatClass)) {
                /** return straight away - don't finish the while loop */
                return true;
            }
            i ++;
        }
        return false;
    }
}
