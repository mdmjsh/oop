package com.oop.abs;

public class Plane implements Comparable<Plane> {

    String name;
    int capacity;
    Boolean available = true;
    Flight flight;

    public Plane(String name, int capacity) throws CapacityValidationException {
        this.name = name;
        this.capacity = validateCapacity(capacity);

    }
    private int validateCapacity(int capacity) throws CapacityValidationException {
        if (capacity < 1| capacity > 3000)
            throw new CapacityValidationException("Plane capacity must be between 1-3000 seats.");
        return capacity;
    }
    void setFlight(Flight flight) throws CapacityValidationException {
        int requiredSeats = 0;
        for (FlightSection flightSetcion: flight.flightSections){
            /* work out the size of the flight */
            requiredSeats += flightSetcion.columns * flightSetcion.rows;
        }
        if (requiredSeats > capacity){
            throw new CapacityValidationException("Cannot assign Flight to this Plane. Seats available on plane: "
                    + capacity + "seats required by Flight: " + requiredSeats);
        }
        this.available = false;
    }
    void makeAvailable(){
        this.available = true;
    }

    @Override
    public int compareTo(Plane other) {
        if (this.capacity < other.capacity) return -1;
        if (this.capacity == other.capacity) return 0;
        if (this.capacity > other.capacity) return 1;
        return 0;
    }
}

