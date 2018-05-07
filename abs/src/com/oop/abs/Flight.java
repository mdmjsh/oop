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
    public LinkedList<FlightSection> flightSections = new LinkedList<>();
    public Airport source;
    public Airport dest;
    public Date date;
    public int totalSeats;
    private int waitingList = 0;
    private Plane plane;

    /**
     * Associate a flight to an airline or raise an exception **
     *
     * @param: airline - An Airline instance
     * @param: source - An Airport instance
     * @param: dest - An airport instance
     * @param: date - A Java.util.Date instance
     */
    Flight(Airline airline, Airport source, Airport dest, Date date) throws NotFoundException, FlightInvalidException {

        /* n.b handles edge case where client doesn't know that the airline object has been deleted...*/
        if (Airline.find(airline.name) == null) {
            throw new NotFoundException("Airline", airline.name);
        }
        this.airline = airline;

        /* possible refactor - this is not 100% guaranteed for uniqueness */
        this.id = UUID.randomUUID();

        if (!validFlight(source, dest)) {
            throw new FlightInvalidException("Source and Destination of flight cannot be the same");
        }
        this.source = source;
        this.dest = dest;
        /* n.b. should dates be validated that they are not in the past? business decision. I think no. */
        this.date = date;

            /* after we've validated all flight info, add the flight to the airline's flights attribute -
            binary relationship**/
        this.airline.flights.add(this);
    }

    public void setPlane(Plane plane){
        this.plane = plane;
    }

    public Plane getPlane(){
        return this.plane;
    }

    /**
     * convert both names to lowercase and check the source and destination are distinct ***
     *
     * @param: source - An Airport instance
     * @param: dest - An Airport instance
     * @returns - Boolean (truthy)
     */
    private static boolean validFlight(Airport source, Airport dest) {
        /* n.b - here we are interested in comparing the string of the name, not the objects themselves */
        return !source.equals(dest);
    }


    /**
     * Associates a Flight instance with a FlightSection instance.
     * only one FlightSection of each SeatClass is allow on a Flight.
     *
     * @param: flightSection - a FlightSection instance
     */
    public void addFlightSection(FlightSection flightSection) throws NonUniqueItemException, NotFoundException {

        if (existingFlightSection(flightSection)) {
            throw new NonUniqueItemException("Flight already contains a flight section with seat class " + flightSection.seatClass);
        }

        /* create binary association between flightSection and Flight */
        flightSection.flight = this;
        flightSection.generateSeats();
        flightSections.add(flightSection);
        totalSeats += flightSection.rows * flightSection.columns;
    }

    /**
     * iterate the flight sections linked list
     * and check that the seatClass of the input flightSection hasn't already been added
     *
     * @param: flightSection - FlightSection instance
     * @returns: boolean
     **/

    private boolean existingFlightSection(FlightSection flightSection) {
        int i = 0;
        while (i < flightSections.size()) {
            if (flightSections.get(i).seatClass.equals(flightSection.seatClass)) {
                /* return straight away - don't finish the while loop */
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * returns true if any FlightSection on the flight has available seats, otherwise false.
     *
     * @return boolean
     */
    public boolean hasAvailableSeats() {
        for (FlightSection flightSection : flightSections) {
            if (flightSection.hasAvailableSeats()) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if there are any seats available in the flight, if not increment the waiting list.
     *
     * @return waitingList - int
     */
    public int checkWaitingList() {
        if (!hasAvailableSeats()) {
            this.waitingList++;
        }
        return this.waitingList;
    }
}


