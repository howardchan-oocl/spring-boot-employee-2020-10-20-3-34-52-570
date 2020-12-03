package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService = new CompanyService(new CompanyRepository());

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @GetMapping("/{index}")
    public ResponseEntity<Company> getOne(@PathVariable Integer index) {
        Company targetCompany = companyService.findByIndex(index);

        return targetCompany == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(targetCompany);
    }

    @GetMapping("/{index}/employees")
    public ResponseEntity<List<Employee>> getEmployeesOfOneCompany(@PathVariable Integer index) {
        List<Employee> employees = companyService.getEmployeesOfOneCompany(index);
        return ResponseEntity.ok(employees);
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<List<Company>> getAllWithPagination(@RequestParam("page") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        List<Company> companies = this.companyService.findPage(pageIndex, pageSize);

        return ResponseEntity.ok(companies);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company company) {
        Company newCompany = companyService.addOne(company);

        return newCompany;
    }

    @PutMapping("/{index}")
    public ResponseEntity<Company> update(@PathVariable Integer index, @RequestBody Company requestCompany) {
        Company company = companyService.update(index, requestCompany);
        if (company != null) {
            return ResponseEntity.ok(company);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Void> delete(@PathVariable Integer index) {
        boolean isDeleted = companyService.delete(index);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
