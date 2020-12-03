package com.thoughtworks.springbootemployee.model;

import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

public class Company {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String companyName;
    private Integer employeesNumber;
    private List<Employee> employees;

    public Company() {

    }

    public Company(String companyName, Integer employeesNumber, List<Employee> employees) {
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
        this.employees = employees;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setId(String id) {
    }
}
