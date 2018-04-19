package com.oop.abs;

public class SeatClassFullException extends Exception{

    public SeatClassFullException(FlightSection flightSection)
    {
        super("No seats available in " + flightSection.seatClass + " on flight " + flightSection.flight.id);
    }
}