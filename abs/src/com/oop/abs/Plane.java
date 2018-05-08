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

    /** toggle a plane's availability status **/
    void toggleAvailabity(){
        this.available = !(this.available);
    }


    /** implemention of the comparable to enable planes to be compared by their capacity **/
    @Override
    public int compareTo(Plane other) {
        if (this.capacity < other.capacity) return -1;
        if (this.capacity == other.capacity) return 0;
        if (this.capacity > other.capacity) return 1;
        return 0;
    }
}

