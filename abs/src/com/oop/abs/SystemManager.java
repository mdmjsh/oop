package com.oop.abs;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class SystemManager {

    /**
     * This class provides the interface (fa̧cade) to the system.
     * When it is created, the SystemManager has no airport or airline objects linked to it.
     **/

    public  LinkedList<Airport> airports = new LinkedList<>();
    public LinkedList<Airline> airlines = new LinkedList<>();
    public LinkedList<Flight> flights = new LinkedList<>();
//    public static HashMap<String, String> ABSMap = new HashMap<>();


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
            throws FlightSectionValidationException, NonUniqueItemException {
        FlightSection flightSection = new FlightSection(rows, columns, seatClass);
        flight.addFlightSection(flightSection);
        return flightSection;
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
        return flight;
    }



    /**
     * Query the Airline.flightMap HashMap of available flights.
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
                    throw new SeatBookedException(seatId, flight);
                }
                /* if we get to here we've looped all the flightSections and didn't find the seat */
                throw new NotFoundException("Seat with name " + seatId + " not found");
        }
        return seat;
    }

    private static String buildFlightMapKey(String source, String dest, Date date) {
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
//
//            String field_class = checkABSClass(field.getGenericType().toString());
//            if (checkABSClass(field.getGenericType().toString()) !=null){
////                reflexivePrint(field);
//                }
            }
            System.out.println("\n");
        }


    /**
     * Iterate the objects in the system and use reflection to print out their current state attributes
     *
     * @throws IllegalAccessException - thrown if field.get(object)) fails in the above method
     */
    public void displaySystemDetails() throws IllegalAccessException {
        this.reflexivePrint(this);
        for(Airport airport : this.airports){this.reflexivePrint(airport);}
        for(Flight flight : this.flights) {
            this.reflexivePrint(flight);
            this.reflexivePrint(flight.airline);
            this.reflexivePrint(flight.source);
            this.reflexivePrint(flight.dest);
            for (FlightSection flightSection : flight.flightSections) {
                this.reflexivePrint(flightSection);
                for (Seat seat : flightSection.seats){
                    this.reflexivePrint(seat);
                }
            }

        }
    }

//    /** used to assert if a field is from the ABS package or outside - i.e. field.getGenericType()
//     * String field - string representation of the field type
//     * **/
//    private String checkABSClass(String field ){
//        ABSMap.put("class com.oop.abs.Airport", "Airport");
//        ABSMap.put("class com.oop.abs.Airline", "Airline");
//        ABSMap.put("class com.oop.abs.Flight", "Flight");
//        ABSMap.put("class com.oop.abs.FlightSection", "FlightSection");
//        ABSMap.put("class com.oop.abs.Seat", "Seat");
//        ABSMap.put("class com.oop.abs.SeatClass", "SeatClass");
//        ABSMap.put("java.util.LinkedList<com.oop.abs.Seat>", "Seat");
//        return ABSMap.get(field);
//    }


    /*
     * generic method to take any object and print it's variables
     * n.b. this method uses type B polymorhpism to print all object attributes, such that:
     *
     *  protected static <E> void displaySystemDetails(E object) throws IllegalAccessException {
     *       E.print();
     *
     * And then implement a print() method on each Object in the system which would iterate its own attributes.
     * However, the above will not compile as Java is statically typed, the compile is unaware at compile time what is
     * due to the dynamic binding of the Generic type. It is impossible for the compiler to know if E will have a print()
     * method at runtime.
     *
     * A way around this would be to make the Generic extend a the class that is being called to print. Such that:
     *
     * protected static <E extends Airport> void displaySystemDetails(E object) throws IllegalAccessException {
     *
     * This would be an ideal implementation if there was an inhertance hierarchy with one common superclass. This way
     * the given a superclass C the following syntax would enable for calling the print method on any subclass of C.
     *
     * protected static <E extends C> void displaySystemDetails(E object)
     *
     * However, as this implemention contains no such inheritance hierarchy for the reasons as discuss in section xx
     * there would be no superclass to extend in this convient way. As a result, to implement polymorphic system printing
     * would require a generic method for every object type in the system. This verbosity arguably defeats the purpose of
     * using generics.
     */

}