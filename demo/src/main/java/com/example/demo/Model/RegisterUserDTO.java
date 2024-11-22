package com.example.demo.Model;

import lombok.Data;

// menerima data minimal dari client, sehingga hanya email, username, dan password yang diperlukan.

@Data
public class RegisterUserDTO {
    private String email;
    private String userName;
    private String password;
}
