package com.oop.abs;

public interface ABSValidator {

    String name = null;
    String validateName(String name) throws NameValidationException;
}
