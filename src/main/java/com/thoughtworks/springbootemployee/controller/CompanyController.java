package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    List<Company> companies = new ArrayList<>();
    private CompanyService companyService = new CompanyService(new CompanyRepository());

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return ResponseEntity.ok(this.companies);
    }

    @GetMapping("/{index}")
    public ResponseEntity<Company> getOne(@PathVariable Integer index) {
        Company targetCompany = this.companies.get(index);

        return targetCompany == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(targetCompany);
    }

    @GetMapping("/{index}/employees")
    public ResponseEntity<List<Employee>> getEmployeesOfOneCompany(@PathVariable Integer index) {
        List<Employee> employees = this.companies.get(index).getEmployees();

        return ResponseEntity.ok(employees);
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<List<Company>> getAllWithPagination(@RequestParam("page") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        int itemAmountToBeSkip = (pageIndex - 1) * pageSize;

        List<Company> companies = this.companies.stream()
                .skip(itemAmountToBeSkip)
                .limit(pageSize)
                .collect(Collectors.toList());

        return ResponseEntity.ok(companies);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company company) {
        this.companies.add(company);

        return company;
    }

    @PutMapping("/{index}")
    public ResponseEntity<Company> update(@PathVariable Integer index, @RequestBody Company requestCompany) {
        if (this.companies.size() >= index + 1) {
            this.companies.set(index,requestCompany);
            return ResponseEntity.ok(requestCompany);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Void> delete(@PathVariable Integer index) {
        if (this.companies.size() >= index + 1) {
            this.companies.remove(index.intValue());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
