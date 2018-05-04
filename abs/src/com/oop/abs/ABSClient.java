package com.oop.abs;

public class ABSClient {

    public static void main (String args[]) throws NameValidationException, NonUniqueItemException,
            IllegalAccessException {
        
        /* Airports */ 
        SystemManager sm = new SystemManager();
        sm.createAirport("DEN");
        sm.createAirport("DFW");
        sm.createAirport("LON");
        sm.createAirport("JPN");
        sm.createAirport("DEH");
        sm.createAirport("NCE");
        
        sm.displaySystemDetails();

        sm.createAirline("DELTA");
        sm.createAirline("AMER");
        sm.createAirline("JET");
        sm.createAirline("SWEST");
        sm.createAirline("FRONT");

        sm.displaySystemDetails();
        
    }
}
