package com.example.demo.Service;

import com.example.demo.Entity.GeneralUser;
import com.example.demo.Model.RegisterUserDTO;
import com.example.demo.Repository.AuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuserRepository auserRepository;

    public GeneralUser registerUser(RegisterUserDTO registerUserDTO) {
        GeneralUser user = new GeneralUser();
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(registerUserDTO.getPassword());
        user.setUserName(registerUserDTO.getUserName());
        user.setRole(GeneralUser.Role.userGeneral);

        return auserRepository.save(user);
    }
}
