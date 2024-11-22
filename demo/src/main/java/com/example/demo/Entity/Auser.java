package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor

public abstract class Auser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.userGeneral;

    protected Double balance;
    protected String address;
    protected String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    protected LocalDate birthDate;

    public enum Gender {
        male,
        female,
        other
    }

    public enum Role {
        userGeneral,
        ahliGizi
    }
}


