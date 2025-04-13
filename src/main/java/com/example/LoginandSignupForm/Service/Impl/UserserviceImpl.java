package com.example.LoginandSignupForm.Service.Impl;

import com.example.LoginandSignupForm.Model.Users;
import com.example.LoginandSignupForm.Repositary.UserRepositary;
import com.example.LoginandSignupForm.Request.RegisterRequest;
import com.example.LoginandSignupForm.Resposnses.RegisterResponse;
import com.example.LoginandSignupForm.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserserviceImpl implements UserService {

    private final UserRepositary userRepositary;
    private final EmailService emailService;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        Users existingUser = userRepositary.findByEmail(registerRequest.getEmail());

        if (existingUser != null && existingUser.isVerified()) {
            throw new RuntimeException("User already registered and verified.");
        }

        // Build and save new user
        Users users = Users.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword()) // TODO: Hash password in real apps
                .verified(false)
                .build();

        String otp = generateOtp();
        users.setOtp(otp);

        Users savedUser = userRepositary.save(users);
        sendVerificationEmail(savedUser.getEmail(), otp);

        return RegisterResponse.builder()
                .username(users.getUsername())
                .email(users.getEmail())
                .build();
    }

    @Override
    public void verify(String email, String otp) {
        Users users = userRepositary.findByEmail(email);

        if (users == null) {
            throw new RuntimeException("User Not Found");
        } else if (users.isVerified()) {
            throw new RuntimeException("User is Already Verified");
        } else if (otp.equals(users.getOtp())) {
            users.setVerified(true);
            userRepositary.save(users);
        } else {
            throw new RuntimeException("Invalid OTP");
        }
    }

    private String generateOtp() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otpValue);
    }

    private void sendVerificationEmail(String email, String otp) {
        String subject = "Email Verification";
        String body = "Your verification OTP is: " + otp;
        emailService.sendEmail(email, subject, body);
    }
}
