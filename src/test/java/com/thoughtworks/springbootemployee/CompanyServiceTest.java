package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CompanyServiceTest {
    @Test
    public void should_return_all_companies_when_get_all_given_all_companies() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        List<Company> expected = Arrays.asList(new Company("test",1,Arrays.asList(new Employee("1", "test", 18, "male", 10000))));

        when(companyRepository.findAll()).thenReturn(expected);

        //when
        List<Company> companies = companyService.findAll();

        //then
        assertEquals(expected, companies);
    }

    @Test
    public void should_return_a_company_when_get_by_index_given_all_companies() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        Company expected = new Company("test",1,Arrays.asList(new Employee("1", "test", 18, "male", 10000)));

        when(companyRepository.findByIndex(0)).thenReturn(expected);

        //when
        Company company = companyService.findByIndex(0);

        //then
        assertEquals(expected, company);
    }

    @Test
    public void should_return_a_list_of_employees_when_get_by_index_for_employees_given_all_companies() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        List<Employee> expected = Arrays.asList(new Employee("1", "test", 18, "male", 10000));

        when(companyRepository.findByIndexForEmployees(0)).thenReturn(expected);

        //when
        List<Employee> employees = companyService.findByIndexForEmployees(0);

        //then
        assertEquals(expected, employees);
    }

    @Test
    public void should_return_a_fixed_size_list_of_companies_when_get_page_given_all_companies() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        List<Company> expected = Arrays.asList(new Company("test",1,Arrays.asList(new Employee("1", "test", 18, "male", 10000))));

        when(companyRepository.findPage(1,1)).thenReturn(expected);

        //when
        List<Company> companies = companyService.findPage(1,1);

        //then
        assertEquals(expected, companies);
    }
}
