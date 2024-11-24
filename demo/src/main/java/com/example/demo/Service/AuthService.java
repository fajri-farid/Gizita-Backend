package com.example.demo.Service;

import com.example.demo.Entity.GeneralUser;
import com.example.demo.Entity.Auser;
import com.example.demo.Model.RegisterUserDTO;
import com.example.demo.Model.LoginUserDTO;
import java.util.Optional;
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

    public GeneralUser loginUser(LoginUserDTO loginUserDTO) throws Exception {
        Optional<Auser> userOptional = auserRepository.findByEmail(loginUserDTO.getEmail());

        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }

        Auser user = userOptional.get();

        if (!user.getPassword().equals(loginUserDTO.getPassword())) {
            throw new Exception("Invalid credential");
        }

        if (user instanceof GeneralUser) {
            return (GeneralUser) user;
        } else {
            throw new Exception("Invalid user type");
        }
    }
}