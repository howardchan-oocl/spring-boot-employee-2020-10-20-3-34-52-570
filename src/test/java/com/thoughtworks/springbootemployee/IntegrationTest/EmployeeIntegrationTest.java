package com.thoughtworks.springbootemployee.IntegrationTest;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    public void should_return_all_employees_when_get_all_given_employees() throws Exception {
        //given
        Employee employee = new Employee("Howard", 18, "male", 99999);
        employeeRepository.save(employee);

        //when
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Howard"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(99999));

        //then
    }

    @Test
    public void should_return_employee_when_create_employee_given_employees() throws Exception {
        //given
        String employeeAsJson = "{\n" +
                "    \"name\": \"Howard\",\n" +
                "    \"age\": 18,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 99999\n" +
                "}";

        //when
        //then
        mockMvc.perform(post("/employees")
                .contentType(APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Howard"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(99999));

        List<Employee> employees = employeeRepository.findAll();
        assertEquals(1, employees.size());
        assertEquals("Howard", employees.get(0).getName());
        assertEquals(18, employees.get(0).getAge());
    }

    @Test
    public void should_return_employee_when_get_one_given_employees() throws Exception {
        //given
        Employee employee = new Employee("Howard", 18, "male", 99999);
        employeeRepository.save(employee);

        //when
        mockMvc.perform(get("/employees/" + employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Howard"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(99999));

        //then
    }

    @Test
    public void should_return_page_when_get_page_given_employees() throws Exception {
        //given
        Employee employee = new Employee("Howard", 18, "male", 99999);
        Employee employee2 = new Employee("Howard2", 18, "male", 99999);
        Employee employee3 = new Employee("Howard3", 18, "male", 99999);
        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        //when
        mockMvc.perform(get("/employees?page=0&pageSize=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Howard"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(99999))
                .andExpect(jsonPath("$[1].name").value("Howard2"))
                .andExpect(jsonPath("$[1].age").value(18))
                .andExpect(jsonPath("$[1].gender").value("male"))
                .andExpect(jsonPath("$[1].salary").value(99999));

        //then
    }

    @Test
    public void should_return_all_male_employees_when_get_by_gender_given_employees() throws Exception {
        //given
        Employee employee = new Employee("Howard", 18, "male", 99999);
        Employee employee2 = new Employee("Howard2", 18, "female", 99999);
        Employee employee3 = new Employee("Howard3", 18, "male", 99999);
        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        //when
        mockMvc.perform(get("/employees?gender=male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Howard"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(99999))
                .andExpect(jsonPath("$[1].name").value("Howard3"))
                .andExpect(jsonPath("$[1].age").value(18))
                .andExpect(jsonPath("$[1].gender").value("male"))
                .andExpect(jsonPath("$[1].salary").value(99999));

        //then
    }

    @Test
    public void should_return_a_updated_employee_when_update_given_employees() throws Exception {
        //given
        Employee employee = new Employee("Howard", 18, "male", 99999);
        Employee employee2 = new Employee("Howard2", 18, "female", 99999);
        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        String employeeAsJson = "{\n" +
                "    \"name\": \"Updated_Howard\",\n" +
                "    \"age\": 18,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 99999\n" +
                "}";

        //when
        mockMvc.perform(put("/employees/" + employee.getId())
                .contentType(APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated_Howard"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(99999));

        //then
    }

    @Test
    public void should_return_nothing_when_delete_given_employees() throws Exception {
        //given
        Employee employee = new Employee("Howard", 18, "male", 99999);
        Employee employee2 = new Employee("Howard2", 18, "female", 99999);
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        //when
        mockMvc.perform(delete("/employees/" + employee.getId()))
                .andExpect(status().isNoContent());

        //then
    }

    @Test
    public void should_return_not_found_when_delete_an_invalid_employee_given_employees() throws Exception {
        //given
        Employee employee = new Employee("Howard", 18, "male", 99999);
        employeeRepository.save(employee);
        employeeRepository.deleteAll();

        //when
        //then
        mockMvc.perform(delete("/employees/" + employee.getId()))
                .andExpect(status().isNotFound());
    }
}
