package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor

<<<<<<< HEAD
=======
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AhliGizi.class, name = "AdminUser"),
        @JsonSubTypes.Type(value = GeneralUser.class, name = "GeneralUser")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Menggunakan Single Table Inheritance
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)  // Kolom untuk membedakan tipe pengguna

>>>>>>> 3a0af60490d3f1b1e3818e4644e921c013f80c15
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

    protected Double balance = 0.0;
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


