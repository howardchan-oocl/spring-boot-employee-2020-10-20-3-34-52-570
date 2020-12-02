package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.Company;
import com.thoughtworks.springbootemployee.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    List<Company> companies = new ArrayList<>();

    public CompanyRepository() {

    }

    public List<Company> findAll() {
        return null;
    }

    public Company findByIndex(int index) {
        return null;
    }

    public List<Employee> findByIndexForEmployees(int index) {
        return null;
    }

    public List<Company> findPage(int page, int pageSize) {
        return null;
    }
}
