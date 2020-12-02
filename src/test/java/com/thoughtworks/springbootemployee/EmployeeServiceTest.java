package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    @Test
    public void should_return_all_employees_when_get_all_given_all_employees() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> expected = Arrays.asList(new Employee(1, "test", 18, "male", 10000));

        when(employeeRepository.findAll()).thenReturn(expected);

        //when
        List<Employee> employees = employeeService.findAll();

        //then
        assertEquals(expected,employees);
    }

    @Test
    public void should_return_a_employee_when_get_one_given_all_employees() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee expected = new Employee(1, "test", 18, "male", 10000);

        when(employeeRepository.findOne(1)).thenReturn(expected);

        //when
        Employee employees = employeeService.findOne(1);

        //then
        assertEquals(expected,employees);
    }

    @Test
    public void should_return_a_fixed_size_list_of_employees_when_get_page_given_all_employees() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> expected = Arrays.asList(new Employee(1, "test", 18, "male", 10000));

        when(employeeRepository.findPage(1,1)).thenReturn(expected);

        //when
        List<Employee> employees = employeeService.findPage(1,1);

        //then
        assertEquals(expected,employees);
    }
}
