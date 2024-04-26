package org.example.railwayticketbooking.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.railwayticketbooking.dto.EmailDto;
import org.example.railwayticketbooking.dto.LoginDto;
import org.example.railwayticketbooking.dto.SignupDto;
import org.example.railwayticketbooking.model.User;
import org.example.railwayticketbooking.provider.JwtResponse;
import org.example.railwayticketbooking.provider.JwtTokenProvider;
import org.example.railwayticketbooking.service.AuthService;
import org.example.railwayticketbooking.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;





    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = authService.getAllUsers();
        return ResponseEntity.ok(users);
    }



    @PostMapping("/signup")
    @Operation(summary = "Signup user", description = "Signup user and generate token")
    public ResponseEntity<JwtResponse> signup(@RequestBody SignupDto dto) {
        return ResponseEntity.ok(authService.signup(dto));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "User logged and generate token")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/sendVerificationCode")
    public ResponseEntity<?> sendVerificationCode(@Valid @RequestBody EmailDto emailDto) {
        try {
            emailService.sendVerificationCode(emailDto.getTo());
            return ResponseEntity.ok("Verification code sent successfully");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send verification code");
        }
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<?> verifyCode(@RequestBody EmailDto emailDto) {
        String email = emailDto.getTo();
        Boolean code = emailDto.getCode();

        if (emailService.isValidVerificationCode(email, code)) {

            return ResponseEntity.ok("Verification successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid verification code");
        }
    }





}
