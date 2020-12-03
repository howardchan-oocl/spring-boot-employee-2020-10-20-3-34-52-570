package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findByIndex(int index) {
        return companyRepository.findByIndex(index);
    }

    public List<Employee> findByIndexForEmployees(int index) {
        return companyRepository.findByIndexForEmployees(index);
    }

    public List<Company> findPage(int page, int pageSize) {
        return companyRepository.findPage(page,pageSize);
    }

    public List<Employee> getEmployeesOfOneCompany(int index) {
        return companyRepository.getEmployeesOfOneCompany(index);
    }

    public Company addOne(Company company) {
        return companyRepository.addOne(company);
    }

    public Company update(Integer index, Company requestCompany) {
        return companyRepository.update(index,requestCompany);
    }

    public boolean delete(Integer index) {
        return companyRepository.delete(index);
    }
}
