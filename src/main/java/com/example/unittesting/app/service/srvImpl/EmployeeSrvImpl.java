package com.example.unittesting.app.service.srvImpl;

import com.example.unittesting.app.exception.ResourceNotFoundException;
import com.example.unittesting.app.model.Employee;
import com.example.unittesting.app.repository.EmployeeRepository;
import com.example.unittesting.app.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return repository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee request) {
        Optional<Employee> employee = repository.findById(request.getId());

        if (employee.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found");
        }

        if (employee.get().getEmail().equals(request.getEmail()) &&
            employee.get().getFirstName().equals(request.getFirstName()) &&
            employee.get().getLastName().equals(request.getLastName())) {
            throw new RuntimeException("Employee already exists");
        }

        return repository.save(
                Employee.builder()
                        .id(request.getId())
                        .email(Objects.requireNonNullElse(request.getEmail(), employee.get().getEmail()))
                        .firstName(Objects.requireNonNullElse(request.getFirstName(), employee.get().getFirstName()))
                        .lastName(Objects.requireNonNullElse(request.getLastName(), employee.get().getLastName()))
                        .build());
    }

    @Override
    public void deleteEmployeeById(long id) {
        Optional<Employee> employee = repository.findById(id);

        if (employee.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found");
        }

        repository.deleteById(id);
    }
}