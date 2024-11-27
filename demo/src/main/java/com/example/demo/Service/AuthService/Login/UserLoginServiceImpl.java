package com.example.demo.Service.AuthService.Login;

import com.example.demo.Entity.Auser;
import com.example.demo.Entity.GeneralUser;
import com.example.demo.Model.LoginUserDTO;
import com.example.demo.Repository.AuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginServiceImpl implements  UserLoginService{
    @Autowired
    private AuserRepository auserRepository;

    @Override
    public GeneralUser login(LoginUserDTO loginUserDTO) throws Exception {
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
