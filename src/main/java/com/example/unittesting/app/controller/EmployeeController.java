package com.example.unittesting.app.controller;

import com.example.unittesting.app.exception.ResourceNotFoundException;
import com.example.unittesting.app.model.Employee;
import com.example.unittesting.app.service.EmployeeService;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping(value = "/saveEmployee")
    public ResponseEntity<?> saveEmployee(@Validated @RequestBody Employee employee){
        try {
            return ResponseEntity.ok(service.saveEmployee(employee));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(100).body(e.getMessage());
        }
    }

    @PostMapping(value = "/updateEmployee")
    public ResponseEntity<?> updateEmployee(@Validated @RequestBody Employee employee){
        try {
            return ResponseEntity.ok(service.updateEmployee(employee));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(100).body(e.getMessage());
        }
    }

    @GetMapping(value = "/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }
}
