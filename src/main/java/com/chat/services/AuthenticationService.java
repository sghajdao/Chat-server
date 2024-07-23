package com.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chat.dto.AuthenticationRequest;
import com.chat.dto.AuthenticationResponse;
import com.chat.dto.RegisterRequest;
import com.chat.dto.Role;
import com.chat.entities.User;
import com.chat.repositories.UserRepository;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .verifiedEmail(false)
                .role(Role.USER)
                .build();
        User existUser = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (existUser != null)
            return AuthenticationResponse.builder().token(null).message("Already exist").build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).message("Success registration").build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user != null) {
            String password = request.getPassword();
            String encodedPassword = user.getPassword();
            Boolean isPwRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwRight) {
                User user2 = userRepository.findByEmailAndPassword(request.getEmail(), encodedPassword).orElse(null);
                if (user2 != null) {
                    var jwtToken = jwtService.generateToken(user2);
                    return AuthenticationResponse.builder().token(jwtToken).message("Login Success").build();
                } else
                    return AuthenticationResponse.builder().token("").message("Login Field").build();
            } else {
                return AuthenticationResponse.builder().token("").message("Password is incorrect").build();
            }
        } else {
            return AuthenticationResponse.builder().token("").message("Email not exist").build();
        }
    }
}
