package com.example.unittesting.app.service;

import com.example.unittesting.app.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(long id);
    Employee updateEmployee(Employee employee);
    void deleteEmployeeById(long id);
}
