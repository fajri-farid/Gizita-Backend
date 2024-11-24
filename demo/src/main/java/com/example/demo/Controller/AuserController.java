package com.example.demo.Controller;

import com.example.demo.Entity.GeneralUser;
import com.example.demo.Model.ResponseDTO;
import com.example.demo.Model.RegisterUserDTO;
import com.example.demo.Model.LoginUserDTO;
import com.example.demo.Service.AuthService.Login.UserLoginService;
import com.example.demo.Service.AuthService.Regist.UserRegistrationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuserController {
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO<?>> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        try {
            GeneralUser savedUser = userRegistrationService.register(registerUserDTO);
            return ResponseEntity.ok(new ResponseDTO<>(true, "User registered successfully", savedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<?>> loginUser(@RequestBody LoginUserDTO loginUserDTO, HttpSession session) {
        try {
            GeneralUser authenticatedUser = userLoginService.login(loginUserDTO);
            session.setAttribute("user", authenticatedUser);
            return ResponseEntity.ok(new ResponseDTO<>(true, "Login successful", authenticatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO<?>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(new ResponseDTO<>(true, "Logout successful", null));
    }
}
