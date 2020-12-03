package com.thoughtworks.springbootemployee.Exception;

public class IdNotFoundException extends Exception {
    public IdNotFoundException(){
        super("ID NOT FOUND ERROR");
    }
}
