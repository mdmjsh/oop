package com.oop.abs;

import java.util.LinkedList;


/** an airline has a name that must have a length less than 6.
 *
 * No two airlines can have the same name.
 *
 * An airline can have 0 or more flights associated with it.
 *
  */

public class Airline implements ABSValidator {

    public String name;
    public static LinkedList<Airline> airlines = new LinkedList();
    public LinkedList<Flight> flights = new LinkedList();


    public Airline(String name) throws NameValidationException, NonUniqueItemException {
        /** REFACTOR - get rid of the null check if possible **/
        if (find(name) != null) {
            throw new NonUniqueItemException("Airline", name);
        }
        /** no airline with this name found - we can create a new one **/
        this.name = validateName(name);
        airlines.add(this);
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


    /** static - class method, MAKE_GENERIC_? **/
    public static Airline find (String name){
        /**
         * Iterate the static airlines linked list and search for a matching name
         *
         * @param: name - name of the airline being queried
         * @returns: Airline instance for the linked list
         */
        int i = 0;
        while (i < airlines.size()) {
            if (airlines.get(i).name.equals(name)) {
                /** return straight away - don't finish the while loop */
                return airlines.get(i);
            }
            i ++;
        }
        return null;
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