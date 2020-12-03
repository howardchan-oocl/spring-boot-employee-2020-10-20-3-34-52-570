package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getOne(@PathVariable String employeeId) {
        Optional<Employee> employee = employeeService.findById(employeeId);

        return employee.isPresent() ? ResponseEntity.ok(employee.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<List<Employee>> getAllWithPagination(@RequestParam("page") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        Page<Employee> employees = employeeService.findPage(PageRequest.of(pageIndex, pageSize));

        return ResponseEntity.ok(employees.getContent());
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
    public ResponseEntity<Employee> update(@PathVariable String employeeId, @RequestBody Employee requestEmployee) {
        try {
            Employee employee = employeeService.update(employeeId, requestEmployee);
            return ResponseEntity.ok(employee);
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> delete(@PathVariable String employeeId) {
        try {
            employeeService.delete(employeeId);
            return ResponseEntity.noContent().build();
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
