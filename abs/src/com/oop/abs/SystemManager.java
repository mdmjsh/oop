package com.oop.abs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.lang.reflect.Field;

public class SystemManager {
    private Airport airport;


    /**
     * This class provides the interface (fa̧cade) to the system.
     * When it is created, the SystemManager has no airport or airline objects linked to it.
     **/

    public LinkedList<Airport> airports = new LinkedList<>();
    public LinkedList<Airline> airlines = new LinkedList<>();
    public LinkedList<Flight> flights = new LinkedList<>();

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
        Airline airline = new Airline(name);
        this.airlines.add(airline);
        return airline;
    }

    /**
     * create a new Airline instance
     *
     * @param rows      - int, must abide by the validation of Flight section
     * @param columns   - int, must abide by the validation of Flight section
     * @param seatClass - FlightSection.SeatClass enumerator
     **/
    public FlightSection createFlightSection(int rows, int columns, FlightSection.SeatClass seatClass) throws FlightSectionValidationException {
        return new FlightSection(rows, columns, seatClass);
    }


    /**
     * create a new Airline instance
     *
     * @param airline - Airline instance
     * @param source  - Airport Instance
     * @param dest    - Airport Instance
     * @param date    - java.util.Date instance;
     **/
    public Flight createFlight(Airline airline, Airport source, Airport dest, Date date) throws NotFoundException, FlightInvalidException {
        Flight flight = new Flight(airline, source, dest, date);
        this.flights.add(flight);
        return flight;
    }


    public FlightSection createFlightSection(int rows, int columns, FlightSection.SeatClass seatClass, Flight flight) throws FlightSectionValidationException, NonUniqueItemException {
        FlightSection flightSection = new FlightSection(rows, columns, seatClass);
        flight.addFlightSection(flightSection);
        return flightSection;
    }


    /**
     * Query the Airline.flightMap HashMap of available flights.
     * If flights are found check their availability using flight.hasAvailableSeats
     *
     * @param source
     * @param dest
     * @param date
     * @return availableFlights - linkedList of Flight objects
     * @throws NotFoundException - no flights found for query params
     */
    public static LinkedList<Flight> findAvailableFlights(String source, String dest, Date date) throws NotFoundException {
        String key = buildFlightMapKey(source, dest, date);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        LinkedList<Flight> flights = Airline.flightMap.get(key);
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
            throw new NotFoundException("No seats available from " + dest + " from " + source + " found on " + df.format(date));
        }
        return availableFlights;
    }

    private static String buildFlightMapKey(String source, String dest, Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return source + dest + df.format(date);
    }

    /**
     * generic method to take any object and print it's variables
     * n.b. this method uses reflection
     **/
    public static <E extends Object> void displaySystemDetails(E object) throws IllegalAccessException {
        /* iterate the fields in the object using .getClass() and .getDeclaredFields() */
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println(field.getName() + ": " + field.get(object));
        }
    }
}