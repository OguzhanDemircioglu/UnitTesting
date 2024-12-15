package com.example.unittesting.app.service.srvImpl;

import com.example.unittesting.app.exception.ResourceNotFoundException;
import com.example.unittesting.app.model.Employee;
import com.example.unittesting.app.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeSrvImplTest {

    @InjectMocks
    EmployeeSrvImpl service;

    @Mock
    EmployeeRepository repository;

    Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder().id(1L).email("qwe").firstName("qwe").lastName("qwe").build();
    }

    @AfterEach
    void tearDown() {
        employee = null;
    }

    @Nested
    @DisplayName("saveEmployeeNest")
    class saveEmployee {
        @Test
        void isNotNull() {
            given(service.saveEmployee(employee))
                    .willReturn(employee);

            assertThat(service.saveEmployee(employee)).isNotNull();
        }

        @Test
        void successfullySave() {
            given(repository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
            service.saveEmployee(employee); // Gerçek iş mantığı çalışır.
            verify(repository).save(employee);
        }

        @Test
        void ResourceNotFoundException() {
            given(repository.findByEmail(employee.getEmail()))
                    .willReturn(Optional.of(employee));

            assertThrows(ResourceNotFoundException.class, () -> service.saveEmployee(employee));

            verify(repository, never()).save(any(Employee.class));
        }
    }

    @Nested
    class getAllEmployees {
        @Test
        void isNotNull() {
            Employee employee1 = Employee.builder().email("qwe").firstName("qwe").lastName("qwe").build();

            given(repository.findAll())
                    .willReturn(List.of(employee, employee1));

            List<Employee> employees = service.getAllEmployees();

            assertThat(employees).isNotNull();
            assertThat(employees.size()).isEqualTo(2);
        }

        @Test
        void isNull() {
            given(repository.findAll())
                    .willReturn(Collections.emptyList());

            List<Employee> employees = service.getAllEmployees();

            assertThat(employees).isEmpty();
            assertThat(employees.size()).isEqualTo(0);
        }
    }

    @Nested
    class getEmployeeById {
        @Test
        void isInstanceOf() {
            given(repository.findById(employee.getId()))
                    .willReturn(Optional.of(employee));
            Employee eemployee1 = service.getEmployeeById(employee.getId()).get();
            assertThat(eemployee1).isInstanceOf(Employee.class);
        }

        @Test
        void isNotNull() {
            given(repository.findById(employee.getId()))
                    .willReturn(Optional.of(employee));
            Optional<Employee> employee1 = service.getEmployeeById(employee.getId());
            assertThat(employee1).isNotNull();
        }
    }

    @Nested
    class updateEmployee {
        @Test
        void noValue() {
            given(repository.findById(employee.getId()))
                    .willReturn(Optional.empty());

            Employee request = new Employee(1L, "notfound@example.com", "Jane", "Doe");
            assertThrows(ResourceNotFoundException.class, () -> service.updateEmployee(request));
        }

        @Test
        void sameValue() {
            given(repository.findById(employee.getId()))
                    .willReturn(Optional.of(employee));

            assertThrows(RuntimeException.class, () -> service.updateEmployee(employee));
        }

        @Test
        void success() {
            given(repository.findById(employee.getId()))
                    .willReturn(Optional.of(employee));

            Employee updatedEmployee = Employee.builder2()
                    .from(employee)
                    .with(p -> p.setFirstName("Batman"))
                    .build();

            given(repository.save(any(Employee.class))).willReturn(updatedEmployee);

            assertEquals(updatedEmployee.getFirstName(), service.updateEmployee(updatedEmployee).getFirstName());
        }
    }

    @Nested
    class deleteEmployee {
        @Test
        void deleted() {
            given(repository.findById(employee.getId())).willReturn(Optional.of(employee));

            willDoNothing().given(repository).deleteById(employee.getId());
            service.deleteEmployeeById(employee.getId());

            verify(repository, times(1)).deleteById(employee.getId());
        }

        @Test
        void notDeleted() {
            given(repository.findById(employee.getId())).willReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> service.deleteEmployeeById(employee.getId()));

            verify(repository, never()).deleteById(employee.getId());
        }
    }
}