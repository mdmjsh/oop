package com.oop.abs;

public class NotFoundException extends Exception {
    public NotFoundException(String className, String instanceName){
            super(className + " with name " + instanceName + " not found");
            }
    }
