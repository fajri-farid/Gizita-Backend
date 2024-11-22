package com.example.demo.Controller;

import com.example.demo.Entity.Auser;
import com.example.demo.Entity.GeneralUser;
import com.example.demo.Service.AuserService;
import com.example.demo.Model.RegisterUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuserController {
    @Autowired
    private AuserService auserService;

    @PostMapping("/regist")
    public Auser createGeneralUser(@RequestBody RegisterUserDTO generalUserDTO) {
        GeneralUser generalUser = new GeneralUser();
        generalUser.setEmail(generalUserDTO.getEmail());
        generalUser.setPassword(generalUserDTO.getPassword());
        generalUser.setUserName(generalUserDTO.getUserName());
        generalUser.setRole(Auser.Role.userGeneral); // Default role
        return auserService.createAuser(generalUser);
    }
}
