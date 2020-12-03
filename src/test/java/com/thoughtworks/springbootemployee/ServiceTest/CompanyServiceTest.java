package com.thoughtworks.springbootemployee.ServiceTest;

import com.thoughtworks.springbootemployee.Exception.IdNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    @Test
    public void should_return_all_companies_when_get_all_given_all_companies() {
        //given
        List<Company> expected = Arrays.asList(new Company("test", 1, Arrays.asList(new Employee("test", 18, "male", 10000))));

        when(companyRepository.findAll()).thenReturn(expected);

        //when
        List<Company> companies = companyService.findAll();

        //then
        assertEquals(expected, companies);
    }

    @Test
    public void should_return_a_company_when_get_by_id_given_all_companies() {
        //given
        Optional<Company> expected = Optional.of(new Company("test", 1, Arrays.asList(new Employee("test", 18, "male", 10000))));

        when(companyRepository.findById("1")).thenReturn(expected);

        //when
        Optional<Company> company = companyService.findById("1");

        //then
        assertEquals(expected, company);
    }

    @Test
    public void should_return_a_list_of_employees_when_get_by_index_for_employees_given_all_companies() {
        //given
        List<Employee> expected = Arrays.asList(new Employee("test", 18, "male", 10000));
        Optional<Company> company = Optional.of(new Company("test", 1, expected));

        when(companyRepository.findById("1")).thenReturn(company);

        //when
        List<Employee> employees = companyService.findById("1").get().getEmployees();

        //then
        assertEquals(expected, employees);
    }

    @Test
    public void should_return_a_fixed_size_list_of_companies_when_get_page_given_all_companies() {
        //given
        List<Company> listOfCompany = Arrays.asList(new Company("test", 1, Arrays.asList(new Employee("test", 18, "male", 10000))));
        Page<Company> expected = new PageImpl<>(listOfCompany);

        when(companyRepository.findAll(PageRequest.of(1, 1))).thenReturn(expected);

        //when
        Page<Company> companies = companyService.findPage(PageRequest.of(1, 1));

        //then
        assertEquals(expected, companies);
    }

    @Test
    public void should_return_a_company_when_add_one() {
        //given
        Company expected = new Company("test", 1, Arrays.asList(new Employee("test", 18, "male", 10000)));

        when(companyService.addOne(expected)).thenReturn(expected);

        //when
        Company company = companyService.addOne(expected);

        //then
        assertEquals(expected, company);
    }

    @Test
    public void should_return_an_employee_when_update() throws IdNotFoundException {
        //given
        Company expected = new Company("test", 1, Arrays.asList(new Employee("test", 18, "male", 10000)));

        when(companyRepository.existsById("1")).thenReturn(true);
        when(companyRepository.save(expected)).thenReturn(expected);

        //when
        Company company = companyService.update("1", expected);

        //then
        assertEquals(expected, company);
    }

    @Test
    public void should_delete_an_employee_when_delete() throws IdNotFoundException {
        //given

        when(companyRepository.existsById("1")).thenReturn(true);

        //when
        boolean isDeleted = companyService.delete("1");

        //then
        assertTrue(isDeleted);
    }

    @Test
    public void should_throw_exception_when_delete_with_an_invalid_id() {
        //given

        when(companyRepository.existsById("1")).thenReturn(false);

        //when
        IdNotFoundException idNotFoundException = assertThrows(IdNotFoundException.class, () -> companyService.delete("1"));

        //Then
        assertEquals("ID NOT FOUND ERROR", idNotFoundException.getMessage());
    }
}
