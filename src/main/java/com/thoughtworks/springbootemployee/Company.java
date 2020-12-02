package com.thoughtworks.springbootemployee;

import java.util.List;

public class Company {
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
}
