package com.example.demo.Controller;

import com.example.demo.Entity.GeneralUser;
import com.example.demo.Model.ResponseDTO;
import com.example.demo.Model.RegisterUserDTO;
import com.example.demo.Service.AuthService;
import com.example.demo.Model.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<?>> loginUser(@RequestBody LoginUserDTO loginUserDTO, HttpSession session){
        try {
            GeneralUser authenticatedUser = authService.loginUser(loginUserDTO);

            session.setAttribute("user", authenticatedUser);

            return ResponseEntity.ok(
                    new ResponseDTO<>(true, "Login successful", authenticatedUser)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ResponseDTO<>(false, e.getMessage(), null)
            );
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO<?>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(new ResponseDTO<>(true, "Logout successful", null));
    }

    @PutMapping("/profile")
    public ResponseEntity<ResponseDTO<?>> editProfileUser(@RequestBody GeneralUser updatedProfile, HttpSession session) {
        try {
            GeneralUser currentUser = (GeneralUser) session.getAttribute("user");

            if (currentUser == null) {
                return ResponseEntity.status(401).body(new ResponseDTO<>(false, "User not logged in", null));
            }

            GeneralUser updatedUser = authService.editProfileUser(currentUser.getId(), updatedProfile);

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
