package com.thoughtworks.springbootemployee.exception;

public class IdNotFoundException extends Exception {
    public IdNotFoundException(){
        super("ID NOT FOUND ERROR");
    }
}
