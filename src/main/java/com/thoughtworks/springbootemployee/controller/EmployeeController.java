package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.IdNotFoundException;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return employeeService.findAll().stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse getOne(@PathVariable String employeeId) throws IdNotFoundException {
        return employeeMapper.toResponse(employeeService.findById(employeeId));
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getAllWithPagination(@RequestParam("page") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        Page<Employee> employees = employeeService.findPage(PageRequest.of(pageIndex, pageSize));

        return employees.getContent().stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> getAllWithGender(@RequestParam("gender") String gender) {
        return employeeService.findByGender(gender).stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse create(@RequestBody Employee employee) {
        return employeeMapper.toResponse(employeeService.addOne(employee));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable String employeeId, @RequestBody Employee requestEmployee) {
        try {
            return ResponseEntity.ok(employeeMapper.toResponse(employeeService.update(employeeId, requestEmployee)));
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> delete(@PathVariable String employeeId) throws IdNotFoundException {
        employeeService.delete(employeeId);
        return ResponseEntity.noContent().build();
    }
}
