package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.CompanyIdNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeIdNotFoundException;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<CompanyResponse> getAll() {
        return companyService.findAll().stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyResponse getOne(@PathVariable String id) throws CompanyIdNotFoundException {
        return companyMapper.toResponse(companyService.findById(id));
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> getEmployeesOfOneCompany(@PathVariable String id) throws CompanyIdNotFoundException {
        return companyService.findByIdForEmployees(id).stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getAllWithPagination(@RequestParam("page") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        return companyService.findPage(PageRequest.of(pageIndex, pageSize)).stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse create(@RequestBody Company company) {
        return companyMapper.toResponse(companyService.addOne(company));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> update(@PathVariable String id, @RequestBody CompanyRequest requestCompany) {
        try {
            if (requestCompany.getEmployees().stream().anyMatch(employee -> employee.getId().equals(id))) {
                throw new EmployeeIdNotFoundException();
            }
            return ResponseEntity.ok(companyMapper.toResponse(companyService.update(id, companyMapper.toEntity(requestCompany))));
        } catch (CompanyIdNotFoundException | EmployeeIdNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws CompanyIdNotFoundException {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
