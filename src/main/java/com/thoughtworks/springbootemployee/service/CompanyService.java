package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.IdNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Company findById(String id) throws IdNotFoundException {
        if (!companyRepository.existsById(id)) {
            throw new IdNotFoundException();
        }
        return companyRepository.findById(id).get();
    }

    public List<Employee> findByIdForEmployees(String id) throws IdNotFoundException {
        if(!companyRepository.existsById(id)){
            throw new IdNotFoundException();
        }
        return companyRepository.findById(id).get().getEmployees();
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
