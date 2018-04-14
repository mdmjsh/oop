package com.oop.abs;

public interface ABSValidator {

    String validateName(String name) throws NameValidationException;

    void checkUnique(String name) throws NonUniqueItemException;

}
