package com.example.demo.Controller;

import com.example.demo.Entity.GeneralUser;
import com.example.demo.Model.ResponseDTO;
import com.example.demo.Model.RegisterUserDTO;
import com.example.demo.Service.AuthService;
import com.example.demo.Model.LoginUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ResponseDTO<?>> loginUser(@RequestBody LoginUserDTO loginUserDTO){
        try {
            GeneralUser authenticatedUser = authService.loginUser(loginUserDTO);

            return ResponseEntity.ok(
                    new ResponseDTO<>(true, "Login successful", authenticatedUser)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ResponseDTO<>(false, e.getMessage(), null)
            );
        }
    }

}
