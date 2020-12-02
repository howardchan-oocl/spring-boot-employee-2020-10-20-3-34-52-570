package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Company;
import com.thoughtworks.springbootemployee.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    List<Company> companies = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return ResponseEntity.ok(this.companies);
    }

    @GetMapping("/{index}")
    public ResponseEntity<Company> getOne(@PathVariable Integer index) {
        Company targetCompany = this.companies.get(index);

        return targetCompany == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(targetCompany);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company company) {
        this.companies.add(company);

        return company;
    }
}
