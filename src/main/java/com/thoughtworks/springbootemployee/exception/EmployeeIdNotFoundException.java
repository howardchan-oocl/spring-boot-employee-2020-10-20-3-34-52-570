package com.thoughtworks.springbootemployee.exception;

public class EmployeeIdNotFoundException extends Exception {
    public EmployeeIdNotFoundException() {
        super("EMPLOYEE ID NOT FOUND ERROR");
    }
}
