package com.oop.abs;

import java.util.*;


/** an airline has a name that must have a length less than 6.
 *
 * No two airlines can have the same name.
 *
 * An airline can have 0 or more flights associated with it.
 *
  */

public class Airline implements ABSValidator {

    public String name;
    public static LinkedList airlines = new LinkedList();


    public Airline(String name) throws NameValidationException, NonUniqueItemException {
        checkUnique(name);
        this.name = validateName(name);
        airlines.add(this.name);
    }


    /** Check if a name < 6 alphabetic chars and is not already taken **/
    public String validateName(String name) throws NameValidationException {

        /** n.b. the right side of the '||' conditional operator is only evaluated if the left side is False.
         * https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.24
         **/
        if (name == null || name.length() == 0) {
            throw new NameValidationException("Airline name cannot be empty string!");
        }

        if (name.length() >5 ) {
            throw new NameValidationException("Airline name must be less than six characters");
        }

        if (!isAlphabetic(name))
            throw new NameValidationException("Airline name must contain only Alphabetic characters");

        // If we've got this far the name is validated
        return name;
    }

    public void checkUnique(String name) throws NonUniqueItemException{
        /**
         * check to see if the airline name we are trying to instantiate is present in the airlines linked list
         *
         * :param: airline - an airline instance
         * :throws: NonUniqueItemException - if the name is already taken
         */
        int index = airlines.indexOf(name);
        if (index != -1) {
            throw new NonUniqueItemException("Airline", name);
        }
    }

    private boolean isAlphabetic(String name){
        for (int i = 0; i < name.length(); i++) {
            /*** iterate the characters of the string and return false if any are not letters **/
            char c = name.charAt(i);
            if (! Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

}