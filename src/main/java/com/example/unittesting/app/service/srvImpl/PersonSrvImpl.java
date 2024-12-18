package com.example.unittesting.app.service.srvImpl;

import com.example.unittesting.app.model.Person;
import com.example.unittesting.app.repository.PersonRepository;
import com.example.unittesting.app.service.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonSrvImpl implements PersonService {

    private final PersonRepository repository;

    public PersonSrvImpl(PersonRepository repository) {
        this.repository = repository;
    }

    public Person savePerson(Person person) {
        return this.repository.save(person);
    }
}
