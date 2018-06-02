package com.oop.abs;

import java.util.Date;

public class ABSClient {

    public static void main (String args[]) throws NameValidationException, NonUniqueItemException,
            IllegalAccessException, NotFoundException, FlightInvalidException,
            FlightSectionValidationException, SeatBookedException, CapacityValidationException {

        SystemManager sm = new SystemManager();

        /* Planes */
        System.out.println("Creating Planes...");
        sm.createPlane("one", 1);
        sm.createPlane("two", 2);
        sm.createPlane("three", 3);
        sm.createPlane("four", 4);
        sm.createPlane("five", 5);
        sm.createPlane("six", 6);
        sm.createPlane("seven", 7);



        /* Airports */
        System.out.println("Creating Airports...");
        sm.createAirport("DEN");
        sm.createAirport("DFW");
        sm.createAirport("LON");
        sm.createAirport("JPN");
        sm.createAirport("DEH");
        sm.createAirport("NCE");
        Airport lhr = sm.createAirport("LHR");
        Airport sfo = sm.createAirport("SFO");
        Airport jfk = sm.createAirport("JFK");


        System.out.println("Creating Airlines...");
        sm.createAirline("DELTA");
        sm.createAirline("AMER");
        sm.createAirline("JET");
        sm.createAirline("SWEST");
        sm.createAirline("FRONT");
        Airline airline = sm.createAirline("air");
        Airline airline1 = sm.createAirline("bob");
        sm.displaySystemDetails();


        System.out.println("Creating Flights...");
        Flight flight = sm.createFlight(airline, lhr, sfo, new Date());
        Flight flight1 = sm.createFlight(airline, lhr, sfo, new Date());
        Flight flight2 = sm.createFlight(airline1, sfo, jfk, new Date());

        // add flight sections
        System.out.println("Creating Flights...");
        sm.createFlightSection(2, 2, FlightSection.SeatClass.BUSINESS, flight);
        sm.createFlightSection(2, 2, FlightSection.SeatClass.FIRST, flight);

        sm.createFlightSection(2, 2, FlightSection.SeatClass.BUSINESS, flight1);
        sm.createFlightSection(2, 2, FlightSection.SeatClass.FIRST, flight1);

        sm.createFlightSection(2, 2, FlightSection.SeatClass.BUSINESS, flight2);
        sm.createFlightSection(2, 2, FlightSection.SeatClass.FIRST, flight2);

        System.out.println("Creating Planes...");
        sm.createPlane("blah", 250);
        sm.createPlane("bloo", 2550);
        sm.createPlane("blee", 200);

        System.out.println("Associating Flight to Planes...");
        sm.associateFlightToPlane(flight, false);
        sm.associateFlightToPlane(flight1, false);
        sm.associateFlightToPlane(flight2, false);

        System.out.println("Booking seats...");
        sm.bookSeat(flight, "A1");

        sm.displaySystemDetails();
        sm.bookSeat(flight, "A2");
        sm.displaySystemDetails();

        System.out.println("In order traversal of Planes Binary Search Tree");
        BalancedBinaryTree tree = sm.planesBST(sm.planes);
        tree.inorder(tree.bst);


        /* Easter egg - polymorphic print example */
//        sm.displaySystemDetailsPolymorphic(sm.airports.getFirst());
    }
}
