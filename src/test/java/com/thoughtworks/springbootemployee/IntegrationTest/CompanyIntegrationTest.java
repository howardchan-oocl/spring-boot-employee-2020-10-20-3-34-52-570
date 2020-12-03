package com.thoughtworks.springbootemployee.IntegrationTest;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    public void should_return_all_companies_when_get_all_given_companies() throws Exception {
        //given
        List<Employee> employees = Arrays.asList(new Employee("Howard", 18, "male", 99999));
        Company company = new Company("test", 1, employees);
        companyRepository.save(company);

        //when
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value("test"))
                .andExpect(jsonPath("$[0].employeesNumber").value(1));
        //then
    }

    @Test
    public void should_return_a_company_when_get_one_given_companies() throws Exception {
        //given
        List<Employee> employees = Arrays.asList(new Employee("Howard", 18, "male", 99999));
        Company company = new Company("test", 1, employees);
        companyRepository.save(company);

        //when
        mockMvc.perform(get("/companies/" + company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("test"))
                .andExpect(jsonPath("$.employeesNumber").value(1));
        //then
    }

    @Test
    public void should_return_employees_when_employees_of_a_company_given_companies() throws Exception {
        //given
        List<Employee> employees = Arrays.asList(new Employee("Howard", 18, "male", 99999));
        Company company = new Company("test", 1, employees);
        companyRepository.save(company);

        //when
        mockMvc.perform(get("/companies/" + company.getId() + "/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Howard"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(99999));

        //then
    }
}
