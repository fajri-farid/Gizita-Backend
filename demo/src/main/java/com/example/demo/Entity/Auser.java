package com.example.demo.Entity;

import java.time.LocalDate;

public abstract class Auser {
    private long Id;
    private String email;
    private String password;
    private String name;
    private String role;
    protected Double balance;
    protected String address;
    protected String phoneNumber;
    protected Gender gender;
    protected LocalDate birthDate;

    public enum Gender{
        MALE,FEMALE,OTHER
    }

    protected Auser(long Id, String email, String password, String name, String role,
          Double balance, String address, String phoneNumber, Gender gender, LocalDate birthdate){
        this.Id=Id;   this.email=email;   this.password=password;   this.name=name;   this.role=role;
        this.balance=balance;   this.address=address;   this.phoneNumber=phoneNumber;   this.gender=gender;    this.birthDate=birthdate;
    }

    public long getId() {
        return Id;
    }

//    public void setId(long id) {
//        Id = id;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || !email.contains("@")){
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}

