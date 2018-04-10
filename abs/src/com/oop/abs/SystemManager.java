package com.oop.abs;

public class SystemManager {
    private Airport airport;


/**
 createAirport(String n): Creates an airport object and links it to the SystemManager.

 The airport will have a name (code) n; n must have exactly three characters. No two airports can have the same name.
 **/

private Airport createAirport(String name) throws NameValidationException, NonUniqueItemException{
    airport = new Airport(name);
    return airport;
    }
}
