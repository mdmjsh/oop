package com.oop.abs;

class SeatBookedException extends Exception {
    SeatBookedException(String id, Flight flight){
        super("Seat: " + id +" on flight: " + flight.id + " already booked");
    }
}