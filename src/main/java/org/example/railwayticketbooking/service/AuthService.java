package org.example.railwayticketbooking.service;

import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.railwayticketbooking.config.exception.UserNotFoundException;
import org.example.railwayticketbooking.dto.LoginDto;
import org.example.railwayticketbooking.dto.SignupDto;
import org.example.railwayticketbooking.model.Role;
import org.example.railwayticketbooking.model.User;
import org.example.railwayticketbooking.provider.JwtResponse;
import org.example.railwayticketbooking.provider.JwtTokenProvider;
import org.example.railwayticketbooking.repository.RoleRepository;
import org.example.railwayticketbooking.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;

    private final RoleRepository roleRepository;

    public User getUserEntity(SignupDto dto) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("USER"));
        Set<Role> roleSet = new HashSet<>(roles);
        return User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .roles(roleSet)
                .build();
    }

    public JwtResponse signup(SignupDto signupDto) {
        if (signupDto != null) {
            User getUserEntity = getUserEntity(signupDto);
            User user = userRepository.save(getUserEntity);
            String token = jwtTokenProvider.generateToken(user);



            return new JwtResponse(user.getId(), token);

        }
        return null;
    }

    public JwtResponse login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            String token = jwtTokenProvider.generateToken(user);
            return new JwtResponse(user.getId(), token);
        }
        throw new UserNotFoundException(" email: " + loginDto.getEmail() + " or password: " + loginDto.getPassword());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}