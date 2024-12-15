package com.example.unittesting.app.repository;

import com.example.unittesting.app.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder().email("qwe").firstName("qwe").lastName("qwe").build();
    }

    @AfterEach
    void tearDown() {
        employee = new Employee();
    }

    @Test
    void checkSaveMethod() {
        Employee savedEmployee = employeeRepository.save(employee);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);
    }

    @Test
    void findByName() {
        Employee savedEmployee = employeeRepository.save(employee);
        assertThat(employeeRepository.findByName(savedEmployee.getFirstName()))
                .isEqualTo(employee);
    }

    @Test
    void saveEmployee(){
        Employee savedEmployee = employeeRepository.save(employee);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @DisplayName("Employees operation")
    @Test
    void findAllEmployeesList(){
        Employee employee1 = Employee.builder()
                .firstName("John")
                .lastName("Cena")
                .email("cena@gmail,com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        List<Employee> employeeList = employeeRepository.findAll();

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    void findById(){
        employeeRepository.save(employee);
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        assertThat(employeeDB).isNotNull();
    }

    @Test
    void findByEmail(){
        employeeRepository.save(employee);
        Optional<Employee> employeeDB = employeeRepository.findByEmail(employee.getEmail());
        assertThat(employeeDB).isNotNull();
    }

    @DisplayName("update employee operation")
    @Test
    void findById2(){
        employeeRepository.save(employee);

        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("qwe@gmail.com");
        savedEmployee.setFirstName("qweeeee");
        Employee updatedEmployee =  employeeRepository.save(savedEmployee);

        assertThat(updatedEmployee.getEmail()).isEqualTo("qwe@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("qweeeee");
    }

    @DisplayName("deleteById employee operation")
    @Test
    void deleteById(){
        employeeRepository.save(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        assertThat(employeeOptional).isEmpty();
    }

    @DisplayName("findByJPQL using JPQL with index")
    @Test
    void findByJPQL(){
        employeeRepository.save(employee);
        String firstName = "qwe";
        String lastName = "qwe";
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    void findByJPQLNamedParams(){
        employeeRepository.save(employee);
        String firstName = "qwe";
        String lastName = "qwe";
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    void findByNativeSQL(){
        employeeRepository.save(employee);
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    void findByNativeSQLNamed(){
        employeeRepository.save(employee);
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());
        assertThat(savedEmployee).isNotNull();
    }

}
