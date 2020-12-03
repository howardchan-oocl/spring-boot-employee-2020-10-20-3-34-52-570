package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    List<Company> companies = new ArrayList<>();

    public CompanyRepository() {

    }

    public List<Company> findAll() {
        return companies;
    }

    public Company findByIndex(int index) {
        return companies.get(index);
    }

    public List<Employee> findByIndexForEmployees(int index) {
        return null;
    }

    public List<Company> findPage(int page, int pageSize) {
        int itemAmountToBeSkip = (page - 1) * pageSize;

        List<Company> companies = this.companies.stream()
                .skip(itemAmountToBeSkip)
                .limit(pageSize)
                .collect(Collectors.toList());

        return companies;
    }

    public List<Employee> getEmployeesOfOneCompany(int index) {
        return this.companies.get(index).getEmployees();
    }

    public Company addOne(Company company) {
        this.companies.add(company);
        return company;
    }

    public Company update(Integer index, Company requestCompany) {
        if (this.companies.size() >= index + 1) {
            this.companies.set(index, requestCompany);
            return requestCompany;
        } else {
            return null;
        }
    }

    public boolean delete(Integer index) {
        if (this.companies.size() >= index + 1) {
            this.companies.remove(index.intValue());
            return true;
        } else {
            return false;
        }
    }
}
