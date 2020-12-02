package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findOne(int id) {
        return employeeRepository.findOne(id);
    }

    public List<Employee> findPage(int page, int pageSize) {
        return employeeRepository.findPage(page,pageSize);
    }
}
