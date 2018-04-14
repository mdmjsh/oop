package com.oop.abs;

public class NameValidationException extends Exception
{
    public NameValidationException(String msg)
    {
        super("A flight section can contain at most 100 rows and 10 columns");
    }
}


