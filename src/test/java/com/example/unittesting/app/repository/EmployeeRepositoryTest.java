package com.example.unittesting.app.repository;

import com.example.unittesting.app.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class EmployeeRepositoryTest {

    @Mock
    private EmployeeRepository repository;

    @Test
    void checkSaveMethod() {
        Employee employee = Employee.builder().email("qwe").firstName("qwe").lastName("123").build();
        when(repository.save(employee)).thenReturn(employee);

        // save() metodunu çağırıyoruz
        Employee savedEmployee = repository.save(employee);

        // Sonucun null olmadığını doğruluyoruz
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);
    }

}