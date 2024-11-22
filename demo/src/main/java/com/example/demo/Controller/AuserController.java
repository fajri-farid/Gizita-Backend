package com.example.demo.Controller;

import com.example.demo.Entity.GeneralUser;
import com.example.demo.Model.ResponseDTO;
import com.example.demo.Model.RegisterUserDTO;
import com.example.demo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuserController {
    @Autowired
    private AuthService authService;

    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO<?>> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        try {
            GeneralUser savedUser = authService.registerUser(registerUserDTO);

            return ResponseEntity.ok(new ResponseDTO<>(true, "User registered successfully", savedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<ResponseDTO<?>> editProfileUser(@PathVariable int id, @RequestBody GeneralUser updatedProfile) {
        try {
            GeneralUser updatedUser = authService.editProfileUser(id, updatedProfile);

            return ResponseEntity.ok(new ResponseDTO<>(true, "Profile updated successfully", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, "Failed to update profile: " + e.getMessage(), null));
        }
    }
}
