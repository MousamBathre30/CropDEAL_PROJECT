package com.gateway.ApiGateWay.controller;

import com.gateway.ApiGateWay.model.AuthRequest;
import com.gateway.ApiGateWay.model.AuthResponse;
import com.gateway.ApiGateWay.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        return authService.register(request);
    }

    @GetMapping
    public String getMessage(){
        return "You have to login with /login";
    }
}
