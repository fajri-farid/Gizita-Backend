package com.example.demo.Service;

import com.example.demo.Entity.Auser;
import com.example.demo.Repository.AuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuserService {
    @Autowired
    private AuserRepository auserRepository;

    public Auser createAuser(Auser auser){
        return auserRepository.save(auser);
    }
}
