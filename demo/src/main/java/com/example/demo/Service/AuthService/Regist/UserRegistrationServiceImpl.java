package com.example.demo.Service.AuthService.Regist;

import com.example.demo.Entity.AhliGizi;
import com.example.demo.Entity.Auser;
import com.example.demo.Entity.GeneralUser;
import com.example.demo.Model.RegisterUserDTO;
import com.example.demo.Repository.AuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements  UserRegistrationService{
    @Autowired
    private AuserRepository auserRepository;

    @Override
    public GeneralUser register(RegisterUserDTO registerUserDTO) {
        Auser user;

        if (registerUserDTO.getRole() == Auser.Role.ahliGizi) {
            user = new AhliGizi();
        } else {
            user = new GeneralUser();
        }

        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(registerUserDTO.getPassword());
        user.setUserName(registerUserDTO.getUserName());
        user.setRole(GeneralUser.Role.userGeneral);

        return (GeneralUser) auserRepository.save(user);
    }
}
