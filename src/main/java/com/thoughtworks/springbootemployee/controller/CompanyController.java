package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.IdNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getOne(@PathVariable String id) throws IdNotFoundException {
        Company targetCompany = companyService.findById(id);

        return ResponseEntity.ok(targetCompany);
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<Employee>> getEmployeesOfOneCompany(@PathVariable String id) throws IdNotFoundException {
        List<Employee> employees = companyService.findByIdForEmployees(id);
        return ResponseEntity.ok(employees);
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<List<Company>> getAllWithPagination(@RequestParam("page") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        Page<Company> companies = this.companyService.findPage(PageRequest.of(pageIndex, pageSize));

        return ResponseEntity.ok(companies.getContent());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company company) {
        return companyService.addOne(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> update(@PathVariable String id, @RequestBody Company requestCompany) {
        try {
            Company company = companyService.update(id, requestCompany);
            return ResponseEntity.ok(company);
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws IdNotFoundException {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
