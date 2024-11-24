package com.example.demo.Model;

import com.example.demo.Entity.Auser;
import lombok.Data;


@Data
public class RegisterUserDTO {
    private String email;
    private String userName;
    private String password;
    private Auser.Role role;
}
