package com.example.unittesting.app.controller;

import com.example.unittesting.app.model.Employee;
import com.example.unittesting.app.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeService service;

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
    void updateEmployee() {
    }

    @Nested
    class getAllEmployees {
        @Test
        void success() throws Exception {
            BDDMockito.given(service.getAllEmployees()).willReturn(List.of(employee, employee));
            ResultActions resultActions = mockMvc.perform(get("/employee/getAllEmployees")
                    .contentType(MediaType.APPLICATION_JSON));
            resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

    @Nested
    class saveEmployee {
        @Test
        void success() throws Exception {
            BDDMockito.given(service.saveEmployee(employee)).willReturn(employee);

            ResultActions resultActions = mockMvc.perform(post("/employee/saveEmployee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(employee)));

            resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
        }
    }

}