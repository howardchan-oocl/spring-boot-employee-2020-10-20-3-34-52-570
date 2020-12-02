package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.Company;
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
}
