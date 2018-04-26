package com.oop.abs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

/** an airline has a name that must have a length less than 6.
 *
 * No two airlines can have the same name.
 *
 * An airline can have 0 or more flights associated with it.
 *
  */

public class Airline implements ABSValidator {

    String name;
    private static LinkedList<Airline> airlines = new LinkedList<>();
    LinkedList<Flight> flights = new LinkedList<>();
    public static HashMap<String, LinkedList<Flight>> flightMap = new HashMap<>();


    public Airline(String name) throws NameValidationException, NonUniqueItemException {
        /* REFACTOR - get rid of the null check if possible */
        if (find(name) != null) {
            throw new NonUniqueItemException("Airline", name);
        }
        /* no airline with this name found - we can create a new one */
        this.name = validateName(name);
        airlines.add(this);
    }


    /** Check if a name < 6 alphabetic chars and is not already taken **/
    public String validateName(String name) throws NameValidationException {
        if (name == null || name.length() == 0) {
            throw new NameValidationException("Airline name cannot be empty string!");
        }

        if (name.length() >5 ) {
            throw new NameValidationException("Airline name must be less than six characters");
        }

        if (!isAlphabetic(name))
            throw new NameValidationException("Airline name must contain only Alphabetic characters");

        // If we've got this far the name is validated
        return name;
    }


    /**
     * Iterate the static airlines linked list and search for a matching name
     *
     * @param: name - name of the airline being queried
     * @returns: Airline instance for the linked list
     */
    public static Airline find (String name){
        int i = 0;
        while (i < airlines.size()) {
            if (airlines.get(i).name.equals(name)) {
                /* return straight away - don't finish the while loop */
                return airlines.get(i);
            }
            i ++;
        }
        return null;
    }

    private boolean isAlphabetic(String name){
        for (int i = 0; i < name.length(); i++) {
            /* iterate the characters of the string and return false if any are not letters */
            char c = name.charAt(i);
            if (! Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /***
     * Used to build a HashMap `flightMap` of all of the Flights in the system.
     * flightMap has is structured: `dest~source~data`: linkedlist of flights with available seats.
     * This HashMap is then queried by the SystemManager's findAvailableFlights() method.
     *
     * @param flight
     */
     static void buildFlightMap(Flight flight){
//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        String key = flight.dest + "~" + flight.source + "~" + df.format(flight.date);
         String key = buildFlightMapKey(flight.source, flight.dest, flight.date);

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
        return dest + "~" + source + "~" + df.format(date);
    }

}