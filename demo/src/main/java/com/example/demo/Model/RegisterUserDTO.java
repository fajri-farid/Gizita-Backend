package com.example.demo.Model;

import lombok.Data;

// menerima data minimal dari client, sehingga hanya email, username, dan password yang diperlukan.

@Data
public class RegisterUserDTO {
    private String email;
    private String userName;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
