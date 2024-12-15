package com.example.unittesting.app.service.srvImpl;

import com.example.unittesting.app.exception.ResourceNotFoundException;
import com.example.unittesting.app.model.Employee;
import com.example.unittesting.app.repository.EmployeeRepository;
import com.example.unittesting.app.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeSrvImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> employee1 = repository.findByEmail(employee.getEmail());
        if (employee1.isPresent()) {
            throw new ResourceNotFoundException("Employee already exists");
        }
        return repository.save(employee);
    }
}