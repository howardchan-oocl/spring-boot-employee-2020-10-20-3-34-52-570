package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
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

    public Employee findById(int id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findPage(int page, int pageSize) {
        return employeeRepository.findPage(page, pageSize);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee addOne(Employee employee) {
        return employeeRepository.addOne(employee);
    }

    public Employee update(int employeeId, Employee requestEmployee) {
        return employeeRepository.update(employeeId, requestEmployee);
    }

    public boolean delete(int employeeId) {
        return employeeRepository.delete(employeeId);
    }
}
