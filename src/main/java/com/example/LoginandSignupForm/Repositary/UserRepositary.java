package com.example.LoginandSignupForm.Repositary;

import com.example.LoginandSignupForm.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositary extends JpaRepository<Users,Long> {


    Users findByEmail(String email);
}
