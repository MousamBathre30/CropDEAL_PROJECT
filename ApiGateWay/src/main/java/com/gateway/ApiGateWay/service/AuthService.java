package com.gateway.ApiGateWay.service;

import com.gateway.ApiGateWay.config.JwtUtil;
import com.gateway.ApiGateWay.model.AuthRequest;
import com.gateway.ApiGateWay.model.AuthResponse;
import com.gateway.ApiGateWay.model.Role;
import com.gateway.ApiGateWay.model.User;
import com.gateway.ApiGateWay.ropo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository; // ✅ No need for @Autowired

    public String register(AuthRequest request) {
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            return "Username already exists!";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt password
        user.setRole(String.valueOf(Role.valueOf(request.getRole().toUpperCase()))); // Store role as uppercase (FARMER or DEALER)

        userRepository.save(user);
        return "User registered successfully!";
    }

    public AuthResponse authenticate(AuthRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // Manually check password --> db
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Invalid Credentials");
        }

        String role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");

        System.out.println("Role is " + role);

        String token = jwtUtil.generateToken(userDetails.getUsername(), role);
        System.out.println("token is generated "+token);
        return new AuthResponse(token);
    }
}
