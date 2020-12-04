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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    //fix comparison issue
    @Test
    public void should_return_all_companies_when_get_all_given_companies() throws Exception {
        //given
        List<Employee> employees = Arrays.asList(new Employee("Howard", 18, "male", 99999));
        Company company = new Company("test", 1, employees);
        companyRepository.save(company);

        //when
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
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
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Howard"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(99999));

        //then
    }

    @Test
    public void should_return_page_when_get_page_given_companies() throws Exception {
        //given
        List<Employee> employees = Arrays.asList(new Employee("Howard", 18, "male", 99999));
        Company company = new Company("test", 1, employees);
        Company company2 = new Company("test2", 1, employees);
        Company company3 = new Company("test3", 1, employees);
        companyRepository.save(company);
        companyRepository.save(company2);
        companyRepository.save(company3);

        //when
        mockMvc.perform(get("/companies?page=0&pageSize=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].companyName").value("test"))
                .andExpect(jsonPath("$[0].employeesNumber").value(1))
                .andExpect(jsonPath("$[1].companyName").value("test2"))
                .andExpect(jsonPath("$[1].employeesNumber").value(1));

        //then
    }

    @Test
    public void should_return_company_when_add_one_given_companies() throws Exception {
        //given
        String employeeAsJson = "{   \n" +
                "    \"companyName\": \"test\",\n" +
                "    \"employeesNumber\": 1,\n" +
                "    \"employees\": [{\n" +
                "        \"name\": \"Howard3\",\n" +
                "        \"age\": 18,\n" +
                "        \"gender\": \"male\",\n" +
                "        \"salary\": 99999\n" +
                "    }]\n" +
                "}";

        //when
        //then
        mockMvc.perform(post("/companies")
                .contentType(APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyName").value("test"))
                .andExpect(jsonPath("$.employeesNumber").value(1));

        List<Company> companies = companyRepository.findAll();
        assertEquals(1, companies.size());
        assertEquals("test", companies.get(0).getCompanyName());
        assertEquals(1, companies.get(0).getEmployeesNumber());
    }

    @Test
    public void should_return_a_update_company_when_update_given_companies() throws Exception {
        //given
        List<Employee> employees = Arrays.asList(new Employee("Howard", 18, "male", 99999));
        Company company = new Company("test", 1, employees);
        Company company2 = new Company("test2", 1, employees);
        companyRepository.save(company);
        companyRepository.save(company2);
        String employeeAsJson = "{   \n" +
                "    \"companyName\": \"testtesttest\",\n" +
                "    \"employeesNumber\": 1,\n" +
                "    \"employees\": [{\n" +
                "        \"name\": \"Howard3\",\n" +
                "        \"age\": 18,\n" +
                "        \"gender\": \"male\",\n" +
                "        \"salary\": 99999\n" +
                "    }]\n" +
                "}";

        //when
        //then
        mockMvc.perform(put("/companies/" + company.getId())
                .contentType(APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("testtesttest"))
                .andExpect(jsonPath("$.employeesNumber").value(1));
    }

    @Test
    public void should_return_nothing_when_delete_given_companies() throws Exception {
        //given
        List<Employee> employees = Arrays.asList(new Employee("Howard", 18, "male", 99999));
        Company company = new Company("test", 1, employees);
        Company company2 = new Company("test2", 1, employees);
        companyRepository.save(company);
        companyRepository.save(company2);

        //when
        //then
        mockMvc.perform(delete("/companies/" + company.getId()))
                .andExpect(status().isNoContent());
        assertEquals(1, companyRepository.findAll().size());
    }

    @Test
    public void should_return_not_found_when_delete_an_invalid_company_given_companies() throws Exception {
        //given
        List<Employee> employees = Arrays.asList(new Employee("Howard", 18, "male", 99999));
        Company company = new Company("test", 1, employees);
        Company addedCompany = companyRepository.save(company);
        companyRepository.deleteById(addedCompany.getId());

        //when
        //then
        mockMvc.perform(delete("/companies/" + addedCompany.getId()))
                .andExpect(status().isNotFound());
    }
}
