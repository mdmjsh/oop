package com.oop.abs;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SystemManagerTest {

    private static Airline airline;
    private static Airline airline1;
    private static Flight flight;
    private static Flight flight1;
    private static Flight flight2;
    private static Flight flight3;
    private static FlightSection first;
    private static FlightSection first1;
    private static FlightSection first2;
    private static FlightSection first3;
    private static Airport lhr;
    private static Airport sfo;
    private static Airport jfk;

    static {
        try {
            first = new FlightSection(1, 1, FlightSection.SeatClass.FIRST);
        } catch (FlightSectionValidationException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            first1 = new FlightSection(1, 1, FlightSection.SeatClass.FIRST);
        } catch (FlightSectionValidationException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            first2 = new FlightSection(1, 1, FlightSection.SeatClass.FIRST);
        } catch (FlightSectionValidationException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            first3 = new FlightSection(1, 1, FlightSection.SeatClass.FIRST);
        } catch (FlightSectionValidationException e) {
            e.printStackTrace();
        }
    }

    private static Date date = new Date();

    static {
        try {
            /* create new static Airline instances to use in the below tests */
            airline = new Airline("air");
            airline1 = new Airline("bob");
        } catch (NameValidationException | NonUniqueItemException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            /* create a new static Airport instances to use in the below tests */
            lhr = new Airport("LHR");
            sfo = new Airport("SFO");
            jfk = new Airport("JFK");
        } catch (NameValidationException | NonUniqueItemException e) {
            e.printStackTrace();
        }
    }


    static {
        /* create a new static Flight instances to use in the below tests */
        try {
            flight = new Flight(airline, lhr, sfo, date);
            flight.addFlightSection(first);
        } catch (NotFoundException | FlightInvalidException | NonUniqueItemException e) {
            e.printStackTrace();
        }
        try {
            flight1 = new Flight(airline1, lhr, sfo, date);
            try {
                flight1.addFlightSection(first1);
            } catch (NonUniqueItemException e) {
                e.printStackTrace();
            }
        } catch (NotFoundException | FlightInvalidException e) {
            e.printStackTrace();
        }
        try {
            flight2 = new Flight(airline, lhr, jfk, date);
            flight2.addFlightSection(first2);
        } catch (NotFoundException | FlightInvalidException | NonUniqueItemException e) {
            e.printStackTrace();
        }
        try {
            flight3 = new Flight(airline1, lhr, jfk, date);
            flight3.addFlightSection(first3);
        } catch (NotFoundException | FlightInvalidException | NonUniqueItemException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testCreateSystemManager(){
        /* assert SystemManager is created empty */
        SystemManager sm = new SystemManager();
        assertEquals(sm.airlines.size(), 0);
        assertEquals(sm.airports.size(), 0);
        assertEquals(sm.flights.size(), 0);
    }

    @Test
    void testCreate() throws NameValidationException, NonUniqueItemException, NotFoundException,
            FlightInvalidException, FlightSectionValidationException {
        /* Test creating Flights, FlightSections, Airports, Airline through the SystemManager */
        SystemManager sm = new SystemManager();
        airline = sm.createAirline("dev");
        assertEquals(sm.airlines.size(), 1);
        airline1 = sm.createAirline("bla");
        assertEquals(sm.airlines.size(), 2);

        /* assert sm.airlines contains pointers to the composite Airline objects */
        assertEquals(sm.airlines.get(0), airline);
        assertEquals(sm.airlines.get(1), airline1);

        Airport airport5 = sm.createAirport("par");

        /* assert there are three static Airports + the one created in this test */
        assertEquals(sm.airports.size(), 4);
        Airport airport4 = sm.createAirport("mil");
        assertEquals(sm.airports.size(), 5);

        /* assert there are three static Flights + the one created in this test */
        Flight flight = sm.createFlight(airline, lhr, sfo, new Date());
        assertEquals(sm.flights.size(), 1);
        Flight flight1 = sm.createFlight(airline1, lhr, sfo, new Date());
        assertEquals(sm.flights.size(), 2);

        FlightSection flightSection = sm.createFlightSection(10, 10, FlightSection.SeatClass.BUSINESS, flight);
        FlightSection flightSection1 = sm.createFlightSection(10, 10, FlightSection.SeatClass.FIRST, flight1);

    }


    @Test
    void testFindAvailableFlights() throws NotFoundException, SeatBookedException {
        /* at the start of the test, each flight has seats available so two flights should be found */
        LinkedList<Flight> availableFlights = SystemManager.findAvailableFlights("LHR", "SFO", date);
        assertEquals(availableFlights.size(), 2);

        /* assert that correct Flights have been found */
        for (Flight flight : availableFlights){
            assertEquals(flight.source, lhr);
            assertEquals(flight.dest, sfo);
            assertEquals(flight.date, date);
        }

        first1.bookSeat("A1");
        availableFlights = SystemManager.findAvailableFlights("LHR", "SFO", date);
        assertEquals(availableFlights.size(), 1);

        /* assert that `flight` is now the only Flight object present in the linkedList */
        assertEquals(availableFlights.get(0), flight);

        /* assert that the search also find the monoco flight */
        availableFlights = SystemManager.findAvailableFlights("LHR", "JFK", date);
        assertEquals(availableFlights.size(), 2);

        /* assert that correct Flights have been found */
        for (Flight flight : availableFlights){
            assertEquals(flight.source.name, "LHR");
            assertEquals(flight.source, lhr);
            assertEquals(flight.dest.name, "JFK");
            assertEquals(flight.dest, jfk);
            assertEquals(flight.date, date);
        }
    }

    @Test
    void testDisplaySystemDetails() throws IllegalAccessException {
        SystemManager.displaySystemDetails(flight);
    }
}
