package com.oop.abs;

import java.util.Date;

public class ABSClient {

    public static void main (String args[]) throws NameValidationException, NonUniqueItemException, IllegalAccessException,
            NotFoundException, FlightInvalidException, FlightSectionValidationException, SeatBookedException {
        
        /* Airports */ 
        SystemManager sm = new SystemManager();
        sm.createAirport("DEN");
        sm.createAirport("DFW");
        sm.createAirport("LON");
        sm.createAirport("JPN");
        sm.createAirport("DEH");
        sm.createAirport("NCE");
        
//        sm.displaySystemDetails();

        sm.createAirline("DELTA");
        sm.createAirline("AMER");
        sm.createAirline("JET");
        sm.createAirline("SWEST");
        sm.createAirline("FRONT");

//        sm.displaySystemDetails();
//        sm.displaySystemDetailsPolymorphic(sm.airports.getFirst());


        Airport lhr = new Airport("LHR");
        Airport sfo = new Airport("SFO");
        Airport jfk = new Airport("JFK");
        sm.displaySystemDetails();

        Airline airline = sm.createAirline("air");
        Airline airline1 = sm.createAirline("bob");
        sm.displaySystemDetails();

        Flight flight = sm.createFlight(airline, lhr, sfo, new Date());
        Flight flight1 = sm.createFlight(airline, lhr, sfo, new Date());
        Flight flight2 = sm.createFlight(airline1, sfo, jfk, new Date());

//        sm.displaySystemDetails();

        // add flight sections
        sm.createFlightSection(2, 2, FlightSection.SeatClass.BUSINESS, flight);
        sm.createFlightSection(2, 2, FlightSection.SeatClass.FIRST, flight);
//        sm.displaySystemDetails();
        // add book a seat

        sm.bookSeat(flight, "A1");

        sm.displaySystemDetails();
        sm.bookSeat(flight, "A2");
        sm.displaySystemDetails();
    }
}
