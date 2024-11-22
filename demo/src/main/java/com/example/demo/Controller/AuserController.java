    package com.example.demo.Controller;

    import com.example.demo.Entity.Auser;
    import com.example.demo.Service.AuserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class AuserController {
        @Autowired
        private AuserService auserService;

        @PostMapping("/register")
        public Auser createUser(@RequestBody Auser auser){
            return auserService.createAuser(auser);
        }
    }
