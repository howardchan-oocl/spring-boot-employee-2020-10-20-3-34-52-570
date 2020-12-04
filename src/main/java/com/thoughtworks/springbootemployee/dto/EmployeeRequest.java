package com.thoughtworks.springbootemployee.dto;

public class EmployeeRequest {
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;

    public EmployeeRequest(String id, String name, Integer age, String gender, Integer salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public EmployeeRequest() {

    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getSalary() {
        return salary;
    }
}
