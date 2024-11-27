package com.example.demo.Service.AuthService.Login;

import com.example.demo.Entity.GeneralUser;
import com.example.demo.Model.LoginUserDTO;

public interface UserLoginService {
    GeneralUser login(LoginUserDTO loginUserDTO) throws Exception;
}
