package com.example.demo.Repository;

import com.example.demo.Entity.Auser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuserRepository extends JpaRepository<Auser, Integer>{
}
