package com.oop.abs;

import java.util.HashMap;

/**
 *   a seat has an identifier (a seat is identified by a row number
 *   and a column character, where the character is a letter from A to J),
 *   and a status which indicates whether the seat is booked or not.
 **/
public class Seat {
    public String id;
    protected int row;
    public boolean booked = false;
    public FlightSection.SeatClass seatClass;
    private static HashMap<Object, Object> seatMap = new HashMap<>();


    Seat(int column, int row, FlightSection.SeatClass seatClass) {

        seatMap.put(0, 'A');
        seatMap.put(1, 'B');
        seatMap.put(2, 'C');
        seatMap.put(3, 'D');
        seatMap.put(4, 'E');
        seatMap.put(5, 'F');
        seatMap.put(6, 'G');
        seatMap.put(7, 'H');
        seatMap.put(8, 'I');
        seatMap.put(9, 'J');

        /** concat the column mapping and a string representation of the row integer **/
        this.id = seatMap.get(column) + String.valueOf(row);
        /** a seat knows its own seatClass **/
        this.seatClass = seatClass;
        this.row = row;
    }
}
