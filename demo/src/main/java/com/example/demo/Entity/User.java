package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    protected  int id;

    @Column(name = "NAME")
    protected  String name;

    @Column(name = "EMAIL")
    protected  String email;

    @Column(name = "PASSWORD")
    private String password;
}