package com.example.unittesting.app.service.srvImpl;

import com.example.unittesting.app.model.Employee;
import com.example.unittesting.app.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeSrvImplTest {

    @InjectMocks
    EmployeeSrvImpl service;

    @Mock
    EmployeeRepository repository;

    Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder().email("qwe").firstName("qwe").lastName("qwe").build();
    }

    @AfterEach
    void tearDown() {
        employee = null;
    }

    @Test
    void saveEmployee() {
        BDDMockito.given(repository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        BDDMockito.given(repository.save(employee))
                .willReturn(employee);

        assertThat(service.saveEmployee(employee)).isNotNull();
    }
}