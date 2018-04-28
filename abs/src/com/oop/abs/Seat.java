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
    private int column;
    public boolean booked = false;
    public FlightSection.SeatClass seatClass;
    private static HashMap<Object, Object> seatMap = new HashMap<>();
    /* n.b need to use object as can't use primative type for K,V types */

    Seat(int column, int row, FlightSection.SeatClass seatClass) {

        seatMap.put(1, 'A');
        seatMap.put(2, 'B');
        seatMap.put(3, 'C');
        seatMap.put(4, 'D');
        seatMap.put(5, 'E');
        seatMap.put(6, 'F');
        seatMap.put(7, 'G');
        seatMap.put(8, 'H');
        seatMap.put(9, 'I');
        seatMap.put(10, 'J');

        /* concat the column mapping and a string representation of the row integer */
        this.id = seatMap.get(column) + String.valueOf(row);
        /* a seat knows its own seatClass */
        this.seatClass = seatClass;
        this.row = row;
        this.column = column;
    }
}
