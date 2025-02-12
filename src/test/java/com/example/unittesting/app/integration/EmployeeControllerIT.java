package com.example.unittesting.app.integration;

import com.example.unittesting.app.model.Employee;
import com.example.unittesting.app.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder().email("qwe").firstName("qwe").lastName("qwe").build();
    }

    @Nested
    class Employees {
        @Test
        void success() throws Exception {
            ResultActions resultActions = mockMvc.perform(get("/employee/getAllEmployees")
                    .contentType(MediaType.APPLICATION_JSON));
            resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                            CoreMatchers.equalTo(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.equalTo(1)));
        }

        @Test
        public void notFound() throws Exception {
            long employeeId = 1L;
            employeeRepository.save(employee);

            employee.setEmail("updated@gmail.com");

            ResultActions response = mockMvc.perform(put("/getEmployeeById/{id}", employeeId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(employee)));

            response.andExpect(status().isNotFound())
                    .andDo(print());
        }
    }
}