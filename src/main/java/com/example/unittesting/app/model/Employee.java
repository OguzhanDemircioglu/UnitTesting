package com.example.unittesting.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Data
@EqualsAndHashCode(of = {"id"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    @Id
    @SequenceGenerator(name = "seq_kisi", allocationSize = 1)
    @GeneratedValue(generator = "seq_kisi", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 20, name = "firstName")
    private String firstName;

    @Column(length = 20, name = "lastName")
    private String lastName;

    @Column(length = 30, name = "email")
    private String email;
}
