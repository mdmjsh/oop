package com.oop.abs;

public class NonUniqueItemException extends Exception{
    public NonUniqueItemException(String className, String instanceName){
        super(className + " with " + instanceName + " already exists");
    }
}