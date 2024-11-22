package com.example.demo.Service;

import com.example.demo.Entity.AhliGizi;
import com.example.demo.Entity.Auser;
import com.example.demo.Entity.GeneralUser;
import com.example.demo.Entity.User;
import com.example.demo.Model.RegisterUserDTO;
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
            user = new AhliGizi(); // Jika role adalah "ahliGizi", buat instance AhliGizi
        } else {
            user = new GeneralUser(); // Default ke GeneralUser
        }


        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(registerUserDTO.getPassword());
        user.setUserName(registerUserDTO.getUserName());
        user.setRole(GeneralUser.Role.userGeneral);

        return (GeneralUser) auserRepository.save(user);
    }

    public GeneralUser editProfileUser(int id, GeneralUser updatedProfile) {
        // Cari user berdasarkan ID
        GeneralUser existingUser = (GeneralUser) auserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        // Update field yang diperbolehkan
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
