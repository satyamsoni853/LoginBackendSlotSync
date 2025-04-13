package com.example.LoginandSignupForm.Service;

import com.example.LoginandSignupForm.Request.RegisterRequest;
import com.example.LoginandSignupForm.Resposnses.RegisterResponse;

public interface UserService {

    RegisterResponse register(RegisterRequest registerRequest);

    void verify(String email,String otp);
}
