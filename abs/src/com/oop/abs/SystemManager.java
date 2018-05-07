package com.oop.abs;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class SystemManager {

    /**
     * This class provides the interface (fa̧cade) to the system.
     * When it is created, the SystemManager has no airport or airline objects linked to it.
     **/

    public LinkedList<Airport> airports = new LinkedList<>();
    public LinkedList<Airline> airlines = new LinkedList<>();
    public LinkedList<Flight> flights = new LinkedList<>();
    public LinkedList<Plane> planes = new LinkedList<>();
    public static HashMap<String, LinkedList<Flight>> flightMap = new HashMap<>();


    /**
     * create a new Airport instance
     *
     * @param name - string, must abide by the validation of Airport name
     **/
    public Airport createAirport(String name) throws NameValidationException, NonUniqueItemException {
        this.airports = Airport.airports;
        return new Airport(name);
    }

    /**
     * create a new Airline instance
     *
     * @param name - string, must abide by the validation of Airline name
     **/
    public Airline createAirline(String name) throws NameValidationException, NonUniqueItemException {
        System.out.println("creating airline " + name);
        Airline airline = new Airline(name);
        System.out.println("created OK " + name);
        this.airlines = Airline.airlines;
        return airline;
    }

    public Plane createPlane(String name, int capacity) throws CapacityValidationException {
        Plane plane = new Plane(name, capacity);
        this.planes.add(plane);
        return plane;
    }

    public Plane associateFlightToPlane(Flight flight) throws NotFoundException {
        try {
            Plane plane = findAvailablePlane(flight.totalSeats);
            flight.setPlane(plane); // call setter on private attribute //
            plane.toggleAvailabity();
            return plane;

        /* this plane.toggleAvailabity() will fail with a NPE if not found hence this catch */
        }catch (NotFoundException | NullPointerException e) {
            throw new NotFoundException("WARNING! no available Planes found for Flight. " +
                    "Flight is not currently associated with any Plane");
        }
    }

    /**
     * create a new Flight instance
     *
     * @param airline - Airline instance
     * @param source  - Airport Instance
     * @param dest    - Airport Instance
     * @param date    - java.util.Date instance;
     **/
    public Flight createFlight(Airline airline, Airport source, Airport dest, Date date)
            throws NotFoundException, FlightInvalidException {
        Flight flight = new Flight(airline, source, dest, date);
        this.flights.add(flight);
        buildFlightMap(flight);
        return flight;
    }

    /**
     * create a new FlightSection instance
     *
     * @param rows      - int, must abide by the validation of Flight section
     * @param columns   - int, must abide by the validation of Flight section
     * @param seatClass - FlightSection.SeatClass enumerator
     * @param flight - Flight instance
     **/
    public FlightSection createFlightSection(int rows, int columns,
                                             FlightSection.SeatClass seatClass, Flight flight)
            throws FlightSectionValidationException, NonUniqueItemException, NotFoundException {

        FlightSection flightSection = new FlightSection(rows, columns, seatClass);
        flight.addFlightSection(flightSection);
        return flightSection;
    }


    /**
     * Query the static flightMap HashMap of available flights.
     * If flights are found check their availability using flight.hasAvailableSeats
     *
     * @param source - string source Airport name
     * @param dest - string source Airport name
     * @param date - java.utils.Date object
     * @return availableFlights - linkedList of Flight objects
     * @throws NotFoundException - no flights found for query params
     */
    public LinkedList<Flight> findAvailableFlights(String source, String dest, Date date) throws NotFoundException {
        String key = buildFlightMapKey(source, dest, date);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        LinkedList<Flight> flights = flightMap.get(key);
        LinkedList<Flight> availableFlights = new LinkedList<>();

        if (flights == null) {
            throw new NotFoundException("No flights from " + source + " to " + dest + " found on " + df.format(date));
        } else {
            for (Flight flight : flights) {
                if (flight.hasAvailableSeats()) {
                    availableFlights.add(flight);
                }
            }
        }
        if (availableFlights.isEmpty()) {
            throw new NotFoundException("No seats available from " + source + " to " + dest +
                    " found on " + df.format(date));
        }
        return availableFlights;
    }


    /**
     * Book a seat on a given Flight.
     *
     * @param flight - Flight instance
     * @param seatId - Id of seat to book
     * @return seat - the booked seat
     */
    public Seat bookSeat(Flight flight, String seatId) throws NotFoundException, SeatBookedException {
        Seat seat = null;
        for (FlightSection flightSection : flight.flightSections) {
            if (flightSection.getSeatById(seatId) != null)
                try {
                seat = flightSection.bookSeat(seatId);
                return seat;
                } catch (SeatBookedException e) {
                    flight.checkWaitingList();
                    throw new SeatBookedException(seatId, flight);
                }
                /* if we get to here we've looped all the flightSections and didn't find the seat */
                throw new NotFoundException("Seat with name " + seatId + " not found");
        }
        return seat;
    }

    /***
     * Used to build a HashMap `flightMap` of all of the Flights in the system.
     * flightMap has structured: `source~dest~data`: linkedlist of flights with available seats.
     * This HashMap is then queried by the SystemManager's findAvailableFlights() method.
     *
     * @param flight - A Flight instance
     */
    static void buildFlightMap(Flight flight){
        String key = buildFlightMapKey(flight.source.name, flight.dest.name, flight.date);

        /* Perform a look up of the key in the static flightMap */
        LinkedList<Flight> flights = flightMap.get(key);
        if (flights == null){
            /* if the key is not found create a new linkedList */
            flights = new LinkedList<>();
        }
        /* n.b. we don't care if the key is found in the hash map or not,
        either way we'll add the flight to the tail of the linkedList */
        flights.add(flight);
        /* add the linkedList to the flightMap at the corresponding key */
        flightMap.put(key, flights);
    }

    private static String buildFlightMapKey(String source, String dest, Date date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return source + dest + df.format(date);
    }


    /**
     * generic method to take any object and print it's variables
     * n.b. this method uses reflection to inspect the class of the generic input and iterate its values
     *
     * * @throws IllegalAccessException - thrown if field.get(object)) fails in the method
     **/
    private <E> void reflexivePrint(E object) throws IllegalAccessException {


        System.out.println("##################################");
        System.out.println(object.toString());
        /* iterate the fields in the object using .getClass() and .getDeclaredFields() */
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println("---------------------------------");
            System.out.println(field.getName() + ": " + field.get(object));
            System.out.println("Type: " + field.getGenericType());
            System.out.println(object.getClass().getPackage());
        }
        System.out.println("\n");
    }


    /**
     * Iterate the objects in the system and use reflection to print out their current state attributes
     *
     * @throws IllegalAccessException - thrown if field.get(object)) fails in the above method
     */
    public void displaySystemDetails() throws IllegalAccessException {
        reflexivePrint(this);
        for(Plane plane : this.planes){reflexivePrint(plane);}
        for(Airport airport : this.airports){reflexivePrint(airport);}
        for(Airline airline : this.airlines){reflexivePrint(airline);}
        for(Flight flight : this.flights) {
            reflexivePrint(flight);
            reflexivePrint(flight.airline);
            reflexivePrint(flight.source);
            reflexivePrint(flight.dest);
            for (FlightSection flightSection : flight.flightSections) {
                reflexivePrint(flightSection);
                for (Seat seat : flightSection.seats){
                    reflexivePrint(seat);
                }
            }
        }
    }

    /* Turn the planes LinkedList into a balanced binary tree for searching */
    public BalancedBinaryTree planesBST(LinkedList planes){
        return new BalancedBinaryTree(planes);
    }

    /**
     * Search a balanced binary tree for planes with matching the capacity.
     * Only returns the plane is it is available
     * @param capacity - required capacity of the Plane
     * @return
     */
    public Plane findAvailablePlane(int capacity) throws NotFoundException {
        BalancedBinaryTree planes = planesBST(this.planes);
        try {
             return planes.searchCapacity(planes.bst, capacity);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** polymorphic print example method - not implemented in this program **/
    public <E extends Airport> void displaySystemDetailsPolymorphic(E object){ E.printAttributes();}

}