package com.example.demo.Service.AuthService.Regist;

import com.example.demo.Entity.GeneralUser;
import com.example.demo.Model.RegisterUserDTO;

public interface UserRegistrationService {
    GeneralUser register(RegisterUserDTO registerUserDTO);
}
