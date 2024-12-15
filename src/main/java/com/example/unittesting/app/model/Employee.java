package com.example.unittesting.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @SequenceGenerator(name = "seq_employees", allocationSize = 1)
    @GeneratedValue(generator = "seq_employees", strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 20, name = "firstName")
    private String firstName;

    @Column(length = 20, name = "lastName")
    private String lastName;

    @Column(length = 30, name = "email", unique = true)
    private String email;
}
