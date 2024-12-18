package com.example.demo.Service.Profile.EditProfile;

import com.example.demo.Entity.GeneralUser;
import com.example.demo.Repository.AuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements EditUserProfileService{
    @Autowired
    private AuserRepository auserRepository;

    @Override
    public GeneralUser editProfile(int id, GeneralUser updatedProfile) {
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
        if (updatedProfile.getFullName() != null) {
            existingUser.setFullName(updatedProfile.getFullName());
        }
        if (updatedProfile.getBio() != null) {
            existingUser.setBio(updatedProfile.getBio());
        }

        return auserRepository.save(existingUser);
    }

    @Override
    public GeneralUser changePassword(int id, String newPassword) {
        GeneralUser existingUser = (GeneralUser) auserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        existingUser.setPassword(newPassword);

        return auserRepository.save(existingUser);
    }
}
