package com.oop.abs;

import java.util.*;

/** An airport must have a name consisting of exactly
 three alphabetic characters. No two airports can have the same name.
 **/

public class Airport {

    public String name;
    public LinkedList<String> airports = new LinkedList<String>();


    public Airport(String name) throws NameValidationException, NonUniqueItemException
    {
        this.name = validateName(name);
        airports.add(this.name);
    }


    /** Check if a name == 3 alphabetic chars and is not already taken **/
    private String validateName(String name) throws NameValidationException, NonUniqueItemException {

        if (name.length() != 3) {
            throw new NameValidationException("Airport name must be exactly three characters");
        }
        int index = airports.indexOf(name);
        if (index != -1) {
            throw new NonUniqueItemException("Airport", name);
        }
        if (!isAlphabetic(name))
            throw new NameValidationException("Airport name must contain only Alphabetic characters");

        // If we've got this far the name is validated
        return name;
    }


    private boolean isAlphabetic(String name){
        for (int i = 0; i < name.length(); i++) {
            // iterate the characters of the string and return false if any are not letters
            char c = name.charAt(i);
            if (! Character.isLetter(c)) {
               return false;
            }
        }
        return true;
    }

}