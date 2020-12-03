package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exception.IdNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Optional<Company> findById(String id) {
        return companyRepository.findById(id);
    }

    public List<Employee> findByIdForEmployees(String id) {
        return companyRepository.findById(id).isPresent() ? companyRepository.findById(id).get().getEmployees() : null;
    }

    public Page<Company> findPage(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    public Company addOne(Company company) {
        return companyRepository.insert(company);
    }

    public Company update(String id, Company requestCompany) throws IdNotFoundException {
        if (companyRepository.existsById(id)) {
            requestCompany.setId(id);
            return companyRepository.save(requestCompany);
        }
        throw new IdNotFoundException();
    }

    public boolean delete(String id) throws IdNotFoundException {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        throw new IdNotFoundException();
    }
}
