package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService = new EmployeeService(new EmployeeRepository());

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getOne(@PathVariable Integer employeeId) {
        Employee employee = employeeService.findById(employeeId);

        return employee == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(employee);
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<List<Employee>> getAllWithPagination(@RequestParam("page") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        List<Employee> employees = employeeService.findPage(pageIndex, pageSize);

        return ResponseEntity.ok(employees);
    }

    @GetMapping(params = {"gender"})
    public ResponseEntity<List<Employee>> getAllWithGender(@RequestParam("gender") String gender) {
        List<Employee> employees = employeeService.findByGender(gender);

        return ResponseEntity.ok(employees);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        return employeeService.addOne(employee);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> update(@PathVariable Integer employeeId, @RequestBody Employee requestEmployee) {
        Employee employee = employeeService.update(employeeId, requestEmployee);

        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> delete(@PathVariable Integer employeeId) {
        boolean isDeleted = employeeService.delete(employeeId);

        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
