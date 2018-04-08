package com.oop.abs;

import java.util.*;


/** an airline has a name that must have a length less than 6.
 *
 * No two airlines can have the same name.
 *
 * An airline can have 0 or more flights associated with it.
 *
 *
  */


public class Airline {

    public String name;
    public LinkedList<String> airlines = new LinkedList<String>();


    public Airline(String name)
    {
        try {
            this.name = validateName(name);
            airlines.add(this.name);
        }
        catch (NameValidationException ex) {
            System.err.println("NameValidationException" + ex.getMessage());
        }
        catch (NonUniqueItemException ex){
            System.err.println("NonUniqueItemException" + ex.getMessage());
        }
    }


    // Check if a name is <= 3 chars and is not already taken
    private String validateName(String name) throws NameValidationException, NonUniqueItemException {

        if (name == null) {
            throw new NameValidationException("Airline names cannot be null");
        }
        if (name.length() > 5 ) {
            throw new NameValidationException("Airline name: " + name + "is more than five characters");
        }
        int index = airlines.indexOf(name);
        if (index != -1) {
            throw new NonUniqueItemException("Airline", name);
        }
        if (!isAlphabetic(name))
            throw new NameValidationException("Airline name must contain only Alphabetic characters");

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