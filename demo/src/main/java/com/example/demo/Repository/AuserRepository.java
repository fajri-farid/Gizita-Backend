package com.example.demo.Repository;

import com.example.demo.Entity.Auser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuserRepository extends JpaRepository<Auser, Integer>{
    Optional<Auser>findByEmail(String email);
}
