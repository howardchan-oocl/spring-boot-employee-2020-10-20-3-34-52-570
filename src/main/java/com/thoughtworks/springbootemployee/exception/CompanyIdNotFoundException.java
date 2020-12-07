package com.thoughtworks.springbootemployee.exception;

public class CompanyIdNotFoundException extends Exception {
    public CompanyIdNotFoundException() {
        super("COMPANY ID NOT FOUND ERROR");
    }
}
