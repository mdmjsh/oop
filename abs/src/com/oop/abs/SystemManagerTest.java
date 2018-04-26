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
            /* create a new static airline instance to use in the below tests */
            airline = new Airline("air");
            airline1 = new Airline("bob");
        } catch (NameValidationException | NonUniqueItemException e) {
            e.printStackTrace();
        }
    }


    static {
        /* create a new static airline instance to use in the below tests */
        try {
            flight = new Flight(airline, "london", "berlin", date);
            flight.addFlightSection(first);
        } catch (NotFoundException | FlightInvalidException | NonUniqueItemException e) {
            e.printStackTrace();
        }
        try {
            flight1 = new Flight(airline1, "london", "berlin", date);
            try {
                flight1.addFlightSection(first1);
            } catch (NonUniqueItemException e) {
                e.printStackTrace();
            }
        } catch (NotFoundException | FlightInvalidException e) {
            e.printStackTrace();
        }
        try {
            flight2 = new Flight(airline, "london", "monaco", date);
            flight2.addFlightSection(first2);
        } catch (NotFoundException | FlightInvalidException | NonUniqueItemException e) {
            e.printStackTrace();
        }
        try {
            flight3 = new Flight(airline1, "london", "monaco", date);
            flight3.addFlightSection(first3);
        } catch (NotFoundException | FlightInvalidException | NonUniqueItemException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testFindAvailableFlights() throws NotFoundException, SeatBookedException {
        /* at the start of the test, each flight has seats available so two flights should be found */
        LinkedList<Flight> availableFlights = SystemManager.findAvailableFlights("london", "berlin", date);
        assertEquals(availableFlights.size(), 2);

        /* assert that correct Flights have been found */
        for (Flight flight : availableFlights){
            assertEquals(flight.source, "london");
            assertEquals(flight.dest, "berlin");
            assertEquals(flight.date, date);
        }

        first1.bookSeat("A1");
        availableFlights = SystemManager.findAvailableFlights("london", "berlin", date);
        assertEquals(availableFlights.size(), 1);

        /* assert that `flight` is now the only Flight object present in the linkedList */
        assertEquals(availableFlights.get(0), flight);

        /* assert that the search also find the monoco flight */
        availableFlights = SystemManager.findAvailableFlights("london", "monaco", date);
        assertEquals(availableFlights.size(), 2);

        /* assert that correct Flights have been found */
        for (Flight flight : availableFlights){
            assertEquals(flight.source, "london");
            assertEquals(flight.dest, "monaco");
            assertEquals(flight.date, date);
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

        Airport airport = sm.createAirport("par");
        assertEquals(sm.airports.size(), 1);
        Airport airport1 = sm.createAirport("mil");
        assertEquals(sm.airports.size(), 2);
        /* assert sm.airports contains pointers to the composite Airport objects */
        assertEquals(sm.airports.get(0), airport);
        assertEquals(sm.airports.get(1), airport1);

        Flight flight = sm.createFlight(airline, "london", "berlin", new Date());
        assertEquals(sm.flights.size(), 1);
        Flight flight1 = sm.createFlight(airline1, "london", "berlin", new Date());
        assertEquals(sm.flights.size(), 2);
        /* assert sm.flights contains pointers to the composite Flight objects */
        assertEquals(sm.flights.get(0), flight);
        assertEquals(sm.flights.get(1), flight1);


        FlightSection flightSection = sm.createFlightSection(10, 10, FlightSection.SeatClass.BUSINESS, flight);
        FlightSection flightSection1 = sm.createFlightSection(10, 10, FlightSection.SeatClass.FIRST, flight1);
        /* assert that flight section has been added to the flight */
        assertEquals(sm.flights.get(0).flightSections.size(), 1);
        assertEquals(sm.flights.get(1).flightSections.size(), 1);

        /* assert sm.flights contains pointers to the composite FlightSection objects */
        assertEquals(sm.flights.get(0).flightSections.get(0), flightSection);
        assertEquals(sm.flights.get(1).flightSections.get(0), flightSection1);

    }



}
