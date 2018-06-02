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

    private static Date date = new Date();


    @Test
    void testCreateSystemManager() {
        /* assert SystemManager is created empty */
        SystemManager sm = new SystemManager();
        assertEquals(sm.airlines.size(), 0);
        assertEquals(sm.airports.size(), 0);
        assertEquals(sm.flights.size(), 0);
    }

    @Test
    void testCreate() throws NameValidationException, NonUniqueItemException, NotFoundException,
            FlightInvalidException, FlightSectionValidationException, IllegalAccessException {
        /* Test creating Flights, FlightSections, Airports, Airline through the SystemManager */
        SystemManager sm = new SystemManager();
        createData(sm,"blu", "bla", "ble", "blo");
        /* assert there are two static Airlines + the one created in this test */
        assertEquals(sm.airlines.size(), 2);

        /* assert there are three static Airports + the one created in this test */
        assertEquals(sm.airports.size(), 2);

        /* assert there are three static Flights + the one created in this test */
        assertEquals(sm.flights.size(), 2);
        sm.displaySystemDetails();
    }

    @Test
    void testFindAvailableFlights() throws NotFoundException, SeatBookedException, IllegalAccessException,
            NameValidationException, NonUniqueItemException, FlightInvalidException, FlightSectionValidationException {
        /* at the start of the test, each flight has seats available so two flights should be found */
        SystemManager sm = new SystemManager();
        Airport lhr = new Airport("LHR");
        Airport sfo = new Airport("SFO");
        Airport jfk = new Airport("JFK");

        Airline airline = sm.createAirline("king");
        /* create two flight from lhr to sfo on the same day */
        Flight flight = sm.createFlight(airline, lhr, sfo, date);
        Flight flight1 = sm.createFlight(airline, lhr, sfo, date);
        Flight flight2 = sm.createFlight(airline, lhr, jfk, date);
        FlightSection first = sm.createFlightSection(1, 1, FlightSection.SeatClass.FIRST, flight);
        FlightSection first1 = sm.createFlightSection(1, 1, FlightSection.SeatClass.FIRST, flight1);
        sm.createFlightSection(10, 10, FlightSection.SeatClass.FIRST, flight2);

        LinkedList<Flight> availableFlights = sm.findAvailableFlights("LHR", "SFO", date);
        assertEquals(availableFlights.size(), 2);

        /* assert that correct Flights have been found */
        for (Flight fl : availableFlights) {
            assertEquals(fl.source, lhr);
            assertEquals(fl.dest, sfo);
            assertEquals(fl.date, date);
        }

        /* book a seat - now only one is available */
        first.bookSeat("A1");
        availableFlights = sm.findAvailableFlights("LHR", "SFO", date);
        assertEquals(availableFlights.size(), 1);

        /* assert that `flight` is now the only Flight object present in the linkedList */
        assertEquals(availableFlights.get(0), flight1);

        /* assert that the search also find the JFK flight */
        availableFlights = sm.findAvailableFlights("LHR", "JFK", date);

        /* assert that correct Flights have been found */
        for (Flight fl : availableFlights) {
            assertEquals(fl.source.name, "LHR");
            assertEquals(fl.source, lhr);
            assertEquals(fl.dest.name, "JFK");
            assertEquals(fl.dest, jfk);
            assertEquals(fl.date, date);
        }
    }

    @Test
    void testBuildFlightMap() throws NameValidationException, NonUniqueItemException,
            NotFoundException, FlightInvalidException {
        SystemManager sm = new SystemManager();
        Airport lhr = new Airport("LHR");
        Airport sfo = new Airport("SFO");

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
        assertEquals(sm.flightMap.get(key).size(), 2);

        /* add another Flight from lhr to sfo but this time with a different Airline */
        Flight flight2 = sm.createFlight(airline1, lhr, sfo, new Date());

        /* assert that as the fligthMap is static all flights from both airlines are present */
        assertEquals(sm.flightMap.get(key).size(), 3);
        LinkedList<Flight> flights = sm.flightMap.get(key);
        System.out.println(sm.flightMap.get(key));
        assertEquals(sm.flightMap.get(key).size(), 3);

        /* assert that the linked list contains pointers to the Flight object */
        assert flights.get(0) == flight;
        assert flights.get(1) == flight1;
        assert flights.get(2) == flight2;
    }

    @Test
    void testBookSeat() throws NotFoundException, SeatBookedException,
            FlightSectionValidationException, NameValidationException, NonUniqueItemException, FlightInvalidException {
        SystemManager sm = new SystemManager();
        Airline airline = sm.createAirline("air");
        Airport lhr = new Airport("LHR");
        Airport sfo = new Airport("SFO");
        Flight flight = sm.createFlight(airline, lhr, sfo, date);
        FlightSection first = sm.createFlightSection(1, 1, FlightSection.SeatClass.FIRST, flight);

        for (Seat seat : first.seats){System.out.println(seat.id);}
        Seat seat = first.getSeatById("A1");
        /* check seat isn't already booked */
        assertEquals(seat.booked, false);
        seat = sm.bookSeat(flight, "A1");
        /* assert seat is now booked through the SystemManager */
        assertEquals(seat.booked, true);
    }

    @Test
    void testPlane() throws NotFoundException, CapacityValidationException {
        SystemManager sm = new SystemManager();
        Plane one = sm.createPlane("one", 1);
        Plane two = sm.createPlane("two", 2);
        Plane result = sm.findAvailablePlane(1, true);
        assertEquals(result, one);
        result = sm.findAvailablePlane(2, true);
        assertEquals(result, two);
    }

    @Test
    void testAssociationPlaneToFlight() throws CapacityValidationException, NotFoundException,
            FlightInvalidException, FlightSectionValidationException, NonUniqueItemException, NameValidationException {
        SystemManager sm = new SystemManager();
        Airline airline = sm.createAirline("air");
        Airport lhr = new Airport("LHR");
        Airport sfo = new Airport("SFO");

        Plane one = sm.createPlane("one", 1);
        Plane two = sm.createPlane("two", 2);
        Flight flight = sm.createFlight(airline, lhr, sfo, date);

        /* create a flight with 2 seats and associate it to the Plane with two seats */
        sm.createFlightSection(1,2, FlightSection.SeatClass.BUSINESS, flight);
        sm.associateFlightToPlane(flight, true);

        /* assert that the correct Plane was chosen based on its capacity */
        assertEquals(flight.getPlane(), two);

        /* assert the Plane two is no longer available */
        assertEquals(two.available, false);

        Flight flight1 = sm.createFlight(airline, lhr, sfo, date);
        sm.createFlightSection(1,2, FlightSection.SeatClass.BUSINESS, flight1);

        /* assert Exception raised if trying to book on a Plane of capcity 2 again */
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            sm.associateFlightToPlane(flight1, true);
        });
        assertEquals("WARNING! no available Planes found for Flight. " +
                "Flight is not currently associated with any Plane", exception.getMessage());
    }

    @Test
    void testReplaceOverbookedFlights() throws NameValidationException, NonUniqueItemException, NotFoundException,
            FlightInvalidException, FlightSectionValidationException, SeatBookedException, CapacityValidationException {
        SystemManager sm = new SystemManager();
        Airline dky = sm.createAirline("DKY");
        Airport prs = sm.createAirport("PRS");
        Airport jfk = sm.createAirport("JFK");
        Flight flight = sm.createFlight(dky, jfk, prs, date);
        FlightSection first = new FlightSection(1, 1, FlightSection.SeatClass.FIRST);
        flight.addFlightSection(first);
        sm.bookSeat(flight, "A1");

        Plane one = sm.createPlane("one", 1);
        sm.associateFlightToPlane(flight, true);

        /* Test the waiting list whilst we're here, try to book a booked seat */
        assertThrows(SeatBookedException.class, () -> { sm.bookSeat(flight, "A1");});

        /* assert that doing so has incremented the waiting list */
        assertEquals(flight.getWaitingList(), 1);

        /* Make another request to book a seat */
        assertThrows(SeatBookedException.class, () -> { sm.bookSeat(flight, "A1");});

        /* assert the waiting list has been updated again */
        assertEquals(flight.getWaitingList(), 2);

        /* one more time... */
        assertThrows(SeatBookedException.class, () -> { sm.bookSeat(flight, "A1");});
        /* assert the waiting list has been updated again */
        assertEquals(flight.getWaitingList(), 3);

        /* This flight is now very oversubscribed */
        assertEquals(flight.replacementPlaneRequired(), true);


        /* one is not available */
        assertEquals(one.available, false);

        Plane two = sm.createPlane("two", 4);
        sm.replaceOverbookedFlights();

        /* flight will now use Plane two */
        assertEquals(flight.getPlane().name, "two");

        /* as a result Plane one is now available and two unavailable */
        assertEquals(one.available, true);
        assertEquals(two.available, false);

        /* waiting list has been booked */
        assertEquals(flight.getWaitingList(), 0);
    }

    /* Exceptions */

    @Test
    void testSeatBookedExceptionThrown() throws NotFoundException, SeatBookedException,
            NameValidationException, NonUniqueItemException, FlightInvalidException, FlightSectionValidationException {
        SystemManager sm = new SystemManager();
        Airline dky = sm.createAirline("DKY");
        Airport prs = sm.createAirport("PRS");
        Airport jfk = sm.createAirport("JFK");

        Flight flight = sm.createFlight(dky, jfk, prs, new Date());
        FlightSection first = new FlightSection(1, 1, FlightSection.SeatClass.FIRST);
        flight.addFlightSection(first);
        sm.bookSeat(flight, "A1");

        Throwable exception = assertThrows(SeatBookedException.class,
                () -> { sm.bookSeat(flight, "A1");});
        assertEquals("Seat: A1 on flight: " + flight.id + " already booked",
                exception.getMessage());
    }

    @Test
    void testNotFoundExceptionThrown() throws NotFoundException, SeatBookedException {
        /* this flight doesn't exist at all */
        /* use java Calendar utility to update the date */
        SystemManager sm = new SystemManager();
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

        /* this flight exists but not on the given date */
        Throwable exception1 = assertThrows(NotFoundException.class, () -> {
            sm.findAvailableFlights("LHR", "JFK", newDate);
        });
        assertEquals("No flights from LHR to JFK found on " + df.format(newDate), exception1.getMessage());
    }

    /** this flight exists but the seats are fully booked - raise a notFoundException **/
    @Test
    void testNotFoundExceptionThrownFullyBookedFlights() throws NameValidationException, NonUniqueItemException,
            NotFoundException, FlightInvalidException, FlightSectionValidationException, SeatBookedException {
        SystemManager sm = new SystemManager();
        Airline dky = sm.createAirline("DKY");
        Airport prs = sm.createAirport("PRS");
        Airport jfk = sm.createAirport("JFK");
        Date date = new Date();
        Flight flight = sm.createFlight(dky, jfk, prs, new Date());
        FlightSection first = new FlightSection(1, 1, FlightSection.SeatClass.FIRST);
        flight.addFlightSection(first);
        sm.bookSeat(flight, "A1");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Throwable exception2 = assertThrows(NotFoundException.class, () -> {
            sm.findAvailableFlights("JFK", "PRS", date);
        });
        assertEquals("No seats available from JFK to PRS found on " + df.format(date),
                exception2.getMessage());

        /* Test the waiting list whilst we're here, try to book a booked seat */
        assertThrows(SeatBookedException.class, () -> { sm.bookSeat(flight, "A1");});

        /* assert that doing so has incremented the waiting list */
        assertEquals(flight.getWaitingList(), 1);

        /* Make another request to book a seat */
        assertThrows(SeatBookedException.class, () -> { sm.bookSeat(flight, "A1");});

        /* assert the waiting list has been updated again */
        assertEquals(flight.getWaitingList(), 2);
    }


//    /** helper method to create additional test data **/
    void createData(SystemManager sysManager, String airlineName, String airlineName1,
                    String airportName, String airportName1) throws NameValidationException,
            NonUniqueItemException, NotFoundException, FlightInvalidException, FlightSectionValidationException {
        SystemManager sm = new SystemManager();
        Airline airline = sm.createAirline(airlineName);
        Airline airline1 = sysManager.createAirline(airlineName1);

        Airport airport =  sysManager.createAirport(airportName);
        Airport airport1 = sm.createAirport(airportName1);

        Flight flight = sysManager.createFlight(airline, airport, airport1, date);
        Flight flight1 = sysManager.createFlight(airline1, airport, airport1, date);

        sysManager.createFlightSection(2, 2, FlightSection.SeatClass.BUSINESS, flight);
        sysManager.createFlightSection(2, 2, FlightSection.SeatClass.FIRST, flight1);

    }
}
