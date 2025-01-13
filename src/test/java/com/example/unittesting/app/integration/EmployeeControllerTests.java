package com.example.unittesting.app.integration;

import com.example.unittesting.app.model.Employee;
import com.example.unittesting.app.repository.EmployeeRepository;
import com.example.unittesting.app.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerTests {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EmployeeService service;

    @Autowired
    EmployeeRepository repository;

    Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder().email("qwe").firstName("qwe").lastName("qwe").build();
    }

    @AfterEach
    void tearDown() {
        employee = new Employee();
        repository.deleteAll();
    }

    @Nested
    class getAllEmployees {
        @Test
        void success() throws Exception {
            repository.saveAll(List.of(employee));
            ResultActions resultActions = mockMvc.perform(get("/employee/getAllEmployees")
                    .contentType(MediaType.APPLICATION_JSON));
            resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                            CoreMatchers.equalTo(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.equalTo(1)));
        }
    }
}
