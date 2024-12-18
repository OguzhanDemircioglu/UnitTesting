package com.example.unittesting.app.controller;

import com.example.unittesting.app.model.Person;
import com.example.unittesting.app.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/persons")
public class PersonController {

    @Autowired
    PersonService service;

    @PostMapping(value = "/savePerson")
    public ResponseEntity<?> savePerson(Person person) {
        return ResponseEntity.ok().body(this.service.savePerson(person));
    }
}
