package com.oop.abs;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SystemManagerTest {

    private static SystemManager sm = new SystemManager();
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

    /* Set up static variables to be used in tests */
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
            airline = sm.createAirline("air");
            airline1 = sm.createAirline("bob");
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
            flight = sm.createFlight(airline, lhr, sfo, date);
            flight.addFlightSection(first);
        } catch (NotFoundException | FlightInvalidException | NonUniqueItemException e) {
            e.printStackTrace();
        }
        try {
            flight1 = sm.createFlight(airline1, lhr, sfo, date);
            try {
                flight1.addFlightSection(first1);
            } catch (NonUniqueItemException e) {
                e.printStackTrace();
            }
        } catch (NotFoundException | FlightInvalidException e) {
            e.printStackTrace();
        }
        try {
            flight2 = sm.createFlight(airline, lhr, jfk, date);
            flight2.addFlightSection(first2);
        } catch (NotFoundException | FlightInvalidException | NonUniqueItemException e) {
            e.printStackTrace();
        }
        try {
            flight3 = sm.createFlight(airline1, lhr, jfk, date);
            flight3.addFlightSection(first3);
        } catch (NotFoundException | FlightInvalidException | NonUniqueItemException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testCreateSystemManager() {
        /* assert SystemManager is created empty */
        SystemManager sm1 = new SystemManager();
        assertEquals(sm1.airlines.size(), 0);
        assertEquals(sm1.airports.size(), 0);
        assertEquals(sm1.flights.size(), 0);
    }

    @Test
    void testCreate() throws NameValidationException, NonUniqueItemException, NotFoundException,
            FlightInvalidException, FlightSectionValidationException, IllegalAccessException {
        /* Test creating Flights, FlightSections, Airports, Airline through the SystemManager */
        createData(sm,"blu", "bla", "ble", "blo");
        /* assert there are two static Airlines + the one created in this test */
        assertEquals(sm.airlines.size(), 4);

        /* assert there are three static Airports + the one created in this test */
        assertEquals(sm.airports.size(), 5);

        /* assert there are three static Flights + the one created in this test */
        assertEquals(sm.flights.size(), 2);
        sm.displaySystemDetails();
    }

    @Test
    void testFindAvailableFlights() throws NotFoundException, SeatBookedException, IllegalAccessException {
        /* at the start of the test, each flight has seats available so two flights should be found */
        LinkedList<Flight> availableFlights = sm.findAvailableFlights("LHR", "SFO", date);
        assertEquals(availableFlights.size(), 2);

        /* assert that correct Flights have been found */
        for (Flight flight : availableFlights) {
            assertEquals(flight.source, lhr);
            assertEquals(flight.dest, sfo);
            assertEquals(flight.date, date);
        }

        /* book a seat - now only one is available */
        first1.bookSeat("A1");
        availableFlights = sm.findAvailableFlights("LHR", "SFO", date);
        assertEquals(availableFlights.size(), 1);

        /* assert that `flight` is now the only Flight object present in the linkedList */
        assertEquals(availableFlights.get(0), flight);

        /* assert that the search also find the JFK flight */
        availableFlights = sm.findAvailableFlights("LHR", "JFK", date);
        assertEquals(availableFlights.size(), 2);

        /* assert that correct Flights have been found */
        for (Flight flight : availableFlights) {
            assertEquals(flight.source.name, "LHR");
            assertEquals(flight.source, lhr);
            assertEquals(flight.dest.name, "JFK");
            assertEquals(flight.dest, jfk);
            assertEquals(flight.date, date);
        }
    }

    @Test
    void testBuildFlightMap() throws NameValidationException, NonUniqueItemException,
            NotFoundException, FlightInvalidException {
        Airline airline = sm.createAirline("king");
        Airline airline1 = sm.createAirline("queen");
        /* create two flight from lhr to sfo on the same day */
        Flight flight = sm.createFlight(airline, lhr, sfo, new Date());
        Flight flight1 = sm.createFlight(airline, lhr, sfo, new Date());

        /* build a key to query */
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String key = flight.source.name + flight.dest.name + df.format(flight.date);

        /* assert both flights added to the flightMap hashmap at the right key location */
        System.out.println(key);
        System.out.println(sm.flightMap.get(key));
        assertEquals(sm.flightMap.get(key).size(), 4);

        /* add another Flight from lhr to sfo but this time with a different Airline */
        Flight flight2 = sm.createFlight(airline1, lhr, sfo, new Date());

        /* assert that as the fligthMap is static all flights from both airlines are present */
        assertEquals(sm.flightMap.get(key).size(), 5);
        LinkedList<Flight> flights = sm.flightMap.get(key);
        System.out.println(sm.flightMap.get(key));
        assertEquals(sm.flightMap.get(key).size(), 5);

        /* assert that the linked list contains pointers to the Flight object */
        assert flights.get(0) == flight;
        assert flights.get(1) == flight1;
        assert flights.get(2) == flight2;
    }

    @Test
    void testBookSeat() throws NotFoundException, SeatBookedException {
        for (Seat seat : first.seats){System.out.println(seat.id);}
        Seat seat = first3.getSeatById("A1");
        /* check seat isn't already booked */
        assertEquals(seat.booked, false);
        seat = sm.bookSeat(flight3, "A1");
        /* assert seat is now booked through the SystemManager */
        assertEquals(seat.booked, true);
    }

    @Test
    void testPlane() throws NotFoundException, CapacityValidationException {
        Plane one = sm.createPlane("one", 1);
        Plane two = sm.createPlane("two", 2);
        Plane result = sm.findAvailablePlane(1);
        assertEquals(result, one);
        result = sm.findAvailablePlane(2);
        assertEquals(result, two);
    }

    @Test
    void testAssociationPlaneToFlight() throws CapacityValidationException, NotFoundException,
            FlightInvalidException, FlightSectionValidationException, NonUniqueItemException {
        Plane one = sm.createPlane("one", 1);
        Plane two = sm.createPlane("two", 2);
        flight = sm.createFlight(airline1, lhr, sfo, date);

        /* create a flight with 2 seats and associate it to the Plane with two seats */
        sm.createFlightSection(1,2, FlightSection.SeatClass.BUSINESS, flight);
        sm.associateFlightToPlane(flight);

        /* assert that the correct Plane was chosen based on its capacity */
        assertEquals(flight.getPlane(), two);

        /* assert the Plane two is no longer available */
        assertEquals(two.available, false);

        flight1 = sm.createFlight(airline1, lhr, sfo, date);
        sm.createFlightSection(1,2, FlightSection.SeatClass.BUSINESS, flight1);

        /* assert Exception raised if trying to book on a Plane of capcity 2 again */
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            sm.associateFlightToPlane(flight1);
        });
        assertEquals("WARNING! no available Planes found for Flight. Flight is not currently" +
                " associated with any Plane", exception.getMessage());
    }

    /* Exceptions */

    @Test
    void testSeatBookedExceptionThrown() throws NotFoundException, SeatBookedException {
        Seat seat = first3.getSeatById("A1");
        /* n.b. conditional required to handle testing in situ and in isolation */
        if (seat.booked){
            Throwable exception = assertThrows(SeatBookedException.class,
                    () -> { sm.bookSeat(flight3, "A1");});
            assertEquals("Seat: A1 on flight: " + flight3.id + " already booked",
                    exception.getMessage());
            assertEquals(exception.getClass().toString(), "class com.oop.abs.SeatBookedException");
        }
        else {
            sm.bookSeat(flight3, "A1");
            Throwable exception = assertThrows(SeatBookedException.class, () -> {
                sm.bookSeat(flight3, "A1");
            });
            assertEquals("Seat: A1 on flight: " + flight3.id + " already booked", exception.getMessage());
            assertEquals(exception.getClass().toString(), "class com.oop.abs.SeatBookedException");
        }
    }

    @Test
    void testNotFoundExceptionThrown() throws NotFoundException, SeatBookedException {
        /* this flight doesn't exist at all */
        /* use java Calendar utility to update the date */
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        /* Increment date by one day */
        cal.add(Calendar.DATE, +1);
        Date newDate = cal.getTime();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Throwable exception = assertThrows(NotFoundException.class, () -> {
            sm.findAvailableFlights("BWT", "JFK", date);
        });
        assertEquals("No flights from BWT to JFK found on " + df.format(date), exception.getMessage());
        assertEquals(exception.getClass().toString(), "class com.oop.abs.NotFoundException");
//
        /* this flight exists but not on the given date */
        Throwable exception1 = assertThrows(NotFoundException.class, () -> {
            sm.findAvailableFlights("LHR", "JFK", newDate);
        });
        assertEquals("No flights from LHR to JFK found on " + df.format(newDate), exception1.getMessage());
        assertEquals(exception1.getClass().toString(), "class com.oop.abs.NotFoundException");
    }

    /** this flight exists but the seats are fully booked - raise a notFoundException **/
    @Test
    void testNotFoundExceptionThrownFullyBookedFlights() throws NameValidationException, NonUniqueItemException,
            NotFoundException, FlightInvalidException, FlightSectionValidationException, SeatBookedException {

        Airline dky = sm.createAirline("DKY");
        Airport prs = sm.createAirport("PRS");
        Date date = new Date();
        flight = sm.createFlight(dky, jfk, prs, new Date());
        first = new FlightSection(1, 1, FlightSection.SeatClass.FIRST);
        flight.addFlightSection(first);
        sm.bookSeat(flight, "A1");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Throwable exception2 = assertThrows(NotFoundException.class, () -> {
            sm.findAvailableFlights("JFK", "PRS", date);
        });
        assertEquals("No seats available from JFK to PRS found on " + df.format(date),
                exception2.getMessage());
        assertEquals(exception2.getClass().toString(), "class com.oop.abs.NotFoundException");
    }

    /** helper method to create additional test data **/
    void createData(SystemManager sysManager, String airlineName, String airlineName1,
                    String airportName, String airportName1) throws NameValidationException,
            NonUniqueItemException, NotFoundException, FlightInvalidException, FlightSectionValidationException {
        sysManager.createAirline(airlineName);
        sysManager.createAirline(airlineName1);

        sysManager.createAirport(airportName);
        sysManager.createAirport(airportName1);

        Flight flight = sysManager.createFlight(airline, lhr, jfk, new Date());
        Flight flight1 = sysManager.createFlight(airline1, lhr, jfk, new Date());

        sysManager.createFlightSection(2, 2, FlightSection.SeatClass.BUSINESS, flight);
        sysManager.createFlightSection(2, 2, FlightSection.SeatClass.FIRST, flight1);

    }
}
