package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeIdNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Employee findById(String id) throws EmployeeIdNotFoundException {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeIdNotFoundException();
        }
        return employeeRepository.findById(id).get();
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

    public Employee update(String employeeId, Employee requestEmployee) throws EmployeeIdNotFoundException {
        if (employeeRepository.existsById(employeeId)) {
            requestEmployee.setId(employeeId);
            return employeeRepository.save(requestEmployee);
        }
        throw new EmployeeIdNotFoundException();
    }

    public boolean delete(String employeeId) throws EmployeeIdNotFoundException {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            return true;
        }
        throw new EmployeeIdNotFoundException();
    }

    public boolean IsEmployeeExist(String id) {
        return employeeRepository.existsById(id);
    }

}
