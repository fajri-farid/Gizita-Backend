package com.example.demo.Controller;

import com.example.demo.Entity.Student;
import com.example.demo.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/addStudent")
    public String postDetails(@RequestBody Student student){
        studentService.saveDetails(student);

        return "POSTED";
    }
}
