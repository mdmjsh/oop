package com.oop.abs;

import java.util.*;

public class SystemManager {
    private Airport airport;


/**
 * This class provides the interface (fa ̧cade) to the system.
 * When it is created, the SystemManager has no airport or airline objects linked to it.
 **/

public LinkedList airports = new LinkedList();

private Airport createAirport(String name) throws NameValidationException, NonUniqueItemException{
    airport = new Airport(name);
    this.airports.add(airport);
    return airport;
    }
}
