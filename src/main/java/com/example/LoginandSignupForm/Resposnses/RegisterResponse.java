package com.example.LoginandSignupForm.Resposnses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterResponse {
    private String username;
    private String email;
//    private String message;  // Optional, if you want to send success info
//    private boolean status;  // Optional, for indicating success/failure
}
