package com.example.unittesting.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Data
@RequiredArgsConstructor
@Table(schema = "crm",name = "kisi")
public class Kisi {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "surname")
    String surname;
}
