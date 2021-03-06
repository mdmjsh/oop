package com.oop.abs;

import java.util.*;

/** An airport must have a name consisting of exactly
 three alphabetic characters. No two airports can have the same name.
 **/

public class Airport {

    public String name;
    public static LinkedList<Airport> airports = new LinkedList<>();

     Airport(String name) throws NameValidationException, NonUniqueItemException {
        if (find(name.toUpperCase()) != null) {
            throw new NonUniqueItemException("Airport", name.toUpperCase());
        }
        this.name = validateName(name);
        airports.add(this);
    }

    /** Check if a name == 3 alphabetic chars and is not already taken
     *
     *  n.b. we don't need to check for empty string as the name has to be exactly three character, however,
     * a null value would still cause an exception to be thrown here which isn't currently being caught
     **/
    private String validateName(String name) throws NameValidationException {

        if (name.length() != 3) {
            throw new NameValidationException("Airport name must be exactly three characters");
        }

        if (!isAlphabetic(name))
            throw new NameValidationException("Airport name must contain only Alphabetic characters");

        // If we've got this far the name is validated - convert names to upper case
        return name.toUpperCase();
    }

    /**
     * Iterate the static airlines linked list and search for a matching name
     *
     * @param: name - name of the airline being queried
     * @returns: Airport instance for the linked list
     */
    public static Airport find (String name){
        int i = 0;
        while (i < airports.size()) {
            if (airports.get(i).name.equals(name)) {
                /* return straight away - don't finish the while loop */
                return airports.get(i);
            }
            i ++;
        }
        return null;
    }

    private boolean isAlphabetic(String name){
        for (int i = 0; i < name.length(); i++) {
            /* iterate the characters of the string and return false if any are not letters */
            char c = name.charAt(i);
            if (! Character.isLetter(c)) {
               return false;
            }
        }
        return true;
    }

     static void printAttributes(){
        /* here we would actually print all of the attribute values of the class */
        System.out.println("hello bob!");

    }
}