package com.example.demo.Model;

import com.example.demo.Entity.Auser;
import lombok.Data;

@Data
public class LoginUserDTO {

    private String email;
    private String password;
    private Auser.Role role;
}
