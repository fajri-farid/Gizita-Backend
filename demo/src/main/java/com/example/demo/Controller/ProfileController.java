package com.example.demo.Controller;

import com.example.demo.Entity.GeneralUser;
import com.example.demo.Model.ResponseDTO;
import com.example.demo.Service.Profile.EditProfile.EditUserProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController{
    @Autowired
    private EditUserProfileService userProfileService;

    @PutMapping("/profile")
    public ResponseEntity<ResponseDTO<?>> editProfileUser(@RequestBody GeneralUser updatedProfile, HttpSession session) {
        try {
            GeneralUser currentUser = (GeneralUser) session.getAttribute("user");

            if (currentUser == null) {
                return ResponseEntity.status(401).body(new ResponseDTO<>(false, "User not logged in", null));
            }

            GeneralUser updatedUser = userProfileService.editProfile(currentUser.getId(), updatedProfile);
            session.setAttribute("user", updatedUser);

            return ResponseEntity.ok(new ResponseDTO<>(true, "Profile updated successfully", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, "Failed to update profile: " + e.getMessage(), null));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseDTO<?>> getProfile(HttpSession session) {
        GeneralUser user = (GeneralUser) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).body(new ResponseDTO<>(false, "User not logged in", null));
        }

        return ResponseEntity.ok(new ResponseDTO<>(true, "User profile", user));
    }
}