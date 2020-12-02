package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {

    }

    public List<Employee> findAll() {
        return null;
    }
}
