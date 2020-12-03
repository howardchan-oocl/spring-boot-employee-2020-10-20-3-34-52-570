package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.Exception.IdNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    @Test
    public void should_return_all_employees_when_get_all_given_all_employees() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> expected = Arrays.asList(new Employee("1", "test", 18, "male", 10000));

        when(employeeRepository.findAll()).thenReturn(expected);

        //when
        List<Employee> employees = employeeService.findAll();

        //then
        assertEquals(expected, employees);
    }

    @Test
    public void should_return_a_employee_when_get_one_given_all_employees() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Optional<Employee> expected = Optional.of(new Employee("1", "test", 18, "male", 10000));

        when(employeeRepository.findById("1")).thenReturn(expected);

        //when
        Optional<Employee> employee = employeeService.findById("1");

        //then
        assertEquals(expected, employee);
    }

    @Test
    public void should_return_a_fixed_size_list_of_employees_when_get_page_given_all_employees() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> listOfEmployee = Arrays.asList(new Employee("1", "test", 18, "male", 10000));
        Page<Employee> expected = new PageImpl<>(listOfEmployee);

        when(employeeRepository.findAll(PageRequest.of(1, 1))).thenReturn(expected);

        //when
        Page<Employee> employees = employeeService.findPage(PageRequest.of(1, 1));

        //then
        assertEquals(expected, employees);
    }

    @Test
    public void should_return_a_list_of_employees_when_get_employees_with_gender_given_all_employees() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> expected = Arrays.asList(new Employee("1", "test", 18, "male", 10000));


        when(employeeRepository.findByGender("male")).thenReturn(expected);

        //when
        List<Employee> employees = employeeService.findByGender("male");

        //then
        assertEquals(expected, employees);
    }

    @Test
    public void should_return_an_employee_when_add_one() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee expected = new Employee("1", "test", 18, "male", 10000);


        when(employeeRepository.insert(expected)).thenReturn(expected);

        //when
        Employee employee = employeeService.addOne(expected);

        //then
        assertEquals(expected, employee);
    }

    @Test
    public void should_return_an_employee_when_update() throws IdNotFoundException {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee expected = new Employee("1", "test", 18, "male", 10000);

        when(employeeRepository.existsById("1")).thenReturn(true);
        when(employeeRepository.save(expected)).thenReturn(expected);

        //when
        Employee employee = employeeService.update("1", expected);

        //then
        assertEquals(expected, employee);
    }

    @Test
    public void should_delete_an_employee_when_delete() throws IdNotFoundException {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        when(employeeRepository.existsById("1")).thenReturn(true);

        //when
        boolean isDeleted = employeeService.delete("1");

        //then
        assertTrue(isDeleted);
    }

    @Test
    public void should_throw_exception_when_delete_with_an_invalid_id() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        when(employeeRepository.existsById("1")).thenReturn(false);

        //when
        IdNotFoundException idNotFoundException = assertThrows(IdNotFoundException.class, () -> employeeService.delete("1"));

        //Then
        assertEquals("Employee Id Not Found", idNotFoundException.getMessage());
    }
}
