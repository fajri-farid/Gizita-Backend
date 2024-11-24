package com.example.demo.Service;

import com.example.demo.Entity.AhliGizi;
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

    public GeneralUser editProfileUser(int id, GeneralUser updatedProfile) {
        GeneralUser existingUser = (GeneralUser) auserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        if (updatedProfile.getUserName() != null) {
            existingUser.setUserName(updatedProfile.getUserName());
        }
        if (updatedProfile.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updatedProfile.getPhoneNumber());
        }
        if (updatedProfile.getAddress() != null) {
            existingUser.setAddress(updatedProfile.getAddress());
        }
        if (updatedProfile.getBirthDate() != null) {
            existingUser.setBirthDate(updatedProfile.getBirthDate());
        }
        if (updatedProfile.getGender() != null) {
            existingUser.setGender(updatedProfile.getGender());
        }

        return auserRepository.save(existingUser);
    }
}