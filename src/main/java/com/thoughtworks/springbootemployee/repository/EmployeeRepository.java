package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {

    }

    public List<Employee> findAll() {
        return employees;
    }

    public Employee findById(int id) {
        return this.employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> findPage(int page, int pageSize) {
        int itemAmountToBeSkip = (page - 1) * pageSize;

        return this.employees.stream()
                .skip(itemAmountToBeSkip)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> findByGender(String gender) {

        return this.employees.stream()
                .filter(employee -> gender.equalsIgnoreCase(employee.getGender()))
                .collect(Collectors.toList());
    }

    public Employee addOne(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public Employee update(int employeeId, Employee requestEmployee) {
        int index = this.employees.indexOf(this.employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElse(null));

        if (index != -1) {
            this.employees.set(index, requestEmployee);
            return requestEmployee;
        }

        return null;
    }

    public boolean delete(int employeeId) {

        return this.employees.removeIf(employee -> employee.getId().equals(employeeId));
    }
}
