package com.example.unittesting.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "calculators")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
public class Calculator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "first")
    private String first;

    @Column(name = "`second`")
    private String second;
}
