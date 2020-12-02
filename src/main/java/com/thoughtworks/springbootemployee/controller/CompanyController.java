package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
