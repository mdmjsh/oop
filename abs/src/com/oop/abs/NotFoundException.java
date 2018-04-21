package com.oop.abs;

class NotFoundException extends Exception {
    NotFoundException(String className, String instanceName){
            super(className + " with name " + instanceName + " not found");
            }
    }
