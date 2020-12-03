package com.thoughtworks.springbootemployee.Exception;

public class EmployeeIdNotFoundException extends Exception {
    public EmployeeIdNotFoundException(){
        super("Employee Id Not Found");
    }
}
