package com.example.unittesting.app.controller;

import com.example.unittesting.app.exception.ResourceNotFoundException;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
        employee = Employee.builder().id(1L).email("qwe").firstName("qwe").lastName("qwe").build();
    }

    @AfterEach
    void tearDown() {
        employee = new Employee();
    }

    @Nested
    class getAllEmployees {
        @Test
        void success() throws Exception {
            BDDMockito.given(service.getAllEmployees()).willReturn(List.of(employee, employee));
            ResultActions resultActions = mockMvc.perform(get("/employee/getAllEmployees")
                    .contentType(MediaType.APPLICATION_JSON));
            resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                            CoreMatchers.equalTo(2)));
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

    @Nested
    class getAllEmployeeById {
        @Test
        void success() throws Exception {
            BDDMockito.given(service.getEmployeeById(1)).willReturn(Optional.ofNullable(employee));

            ResultActions resultActions = mockMvc.perform(get("/employee/getEmployeeById/{id}", 1));
            resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())));
        }
        @Test
        void notFound() throws Exception {
            BDDMockito.given(service.getEmployeeById(1)).willReturn(Optional.empty());

            ResultActions resultActions = mockMvc.perform(get("/employee/getEmployeeById/{id}", 1));
            resultActions.andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andDo(MockMvcResultHandlers.print());
        }
    }

    @Nested
    class updateEmployee {
        @Test
        void isBadRequest() throws Exception {
            BDDMockito.given(service.updateEmployee(employee)).willReturn(employee);

            ResultActions resultActions = mockMvc.perform(post("/employee/updateEmployee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString("employee")));

            resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
        @Test
        void isUnsupportedMediaType() throws Exception {
            BDDMockito.given(service.updateEmployee(employee)).willReturn(employee);

            ResultActions resultActions = mockMvc.perform(post("/employee/updateEmployee")
                    .contentType(MediaType.APPLICATION_ATOM_XML)
                    .content(objectMapper.writeValueAsString(employee)));

            resultActions.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());
        }
        @Test
        void isOk() throws Exception {
            BDDMockito.given(service.updateEmployee(employee)).willReturn(employee);

            ResultActions resultActions = mockMvc.perform(post("/employee/updateEmployee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(employee)));

            resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        }
        @Test
        void isNotFound() throws Exception {
            BDDMockito.given(service.updateEmployee(employee))
                    .willThrow(new RuntimeException("Employee already exists"));

            ResultActions resultActions = mockMvc.perform(post("/employee/updateEmployee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(employee)));

            resultActions.andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().string("Employee already exists"))
                    .andDo(MockMvcResultHandlers.print());
        }
        @Test
        void isNotFound2() throws Exception {
            BDDMockito.given(service.updateEmployee(employee))
                    .willThrow(new ResourceNotFoundException("Employee not found"));

            ResultActions resultActions = mockMvc.perform(post("/employee/updateEmployee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(employee)));

            resultActions.andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().string("Employee not found"))
                    .andDo(MockMvcResultHandlers.print());
        }
    }

    @Nested
    class deleteEmployeeById {
        @Test
        void isOk() throws Exception {
            BDDMockito.doNothing().when(service).deleteEmployeeById(1L);

            mockMvc.perform(delete("/employee/deleteEmployeeById/{id}", 1))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("Employee with ID 1 has been deleted successfully."))
                    .andDo(MockMvcResultHandlers.print());
        }
        @Test
        void isNotFound() throws Exception {
            BDDMockito.doThrow(new ResourceNotFoundException("Employee not found")).when(service).deleteEmployeeById(1L);

            mockMvc.perform(delete("/employee/deleteEmployeeById/{id}", 1))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().string("Employee not found"))
                    .andDo(MockMvcResultHandlers.print());
        }
    }
}