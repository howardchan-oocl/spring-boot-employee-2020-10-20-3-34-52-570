package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exception.IdNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Employee> findById(String id) {
        return employeeRepository.findById(id);
    }

    public Page<Employee> findPage(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee addOne(Employee employee) {
        return employeeRepository.insert(employee);
    }

    public Employee update(String employeeId, Employee requestEmployee) throws IdNotFoundException {
        if (employeeRepository.existsById(employeeId)) {
            requestEmployee.setId(employeeId);
            return employeeRepository.save(requestEmployee);
        }
        throw new IdNotFoundException();
    }

    public boolean delete(String employeeId) throws IdNotFoundException {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            return true;
        }
        throw new IdNotFoundException();
    }
}
