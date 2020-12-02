package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CompanyServiceTest {
    @Test
    public void should_return_all_employees_when_get_all_given_all_employees() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        List<Company> expected = Arrays.asList(new Company("test",1,Arrays.asList(new Employee(1, "test", 18, "male", 10000))));

        when(companyRepository.findAll()).thenReturn(expected);

        //when
        List<Company> companies = companyService.findAll();

        //then
        assertEquals(expected, companies);
    }
}
