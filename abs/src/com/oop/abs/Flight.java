package com.oop.abs;

import java.util.Date;

    /** A flight can be associated with 0 or more flight sections.
     *
     * There can only be one flight section of a particular seat class in a flight
     * All flights for a given airline must have unique flight ids.
     * A flight has an originating airport and a destination airport.
     * The originating and destination airports cannot be the same.
     *
     **/
public class Flight {
    public Airline airline;
    public FlightSection [] flightSections = new FlightSection[3];
    public String source;
    public String dest;
    public Date departure;

//    public Flight(Airline airline, String source, String dest, FlightSection flightSection){
//        try {
//            this.airline = Airline.get(airline);
//        }
//        ()
//
//    }
}
