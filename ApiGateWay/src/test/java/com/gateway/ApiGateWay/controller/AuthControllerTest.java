package com.gateway.ApiGateWay.controller;



import com.gateway.ApiGateWay.model.AuthRequest;
import com.gateway.ApiGateWay.model.AuthResponse;
import com.gateway.ApiGateWay.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerUnitTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        AuthRequest request = new AuthRequest("mousam", "password123" , "FARMER");
        AuthResponse expectedResponse = new AuthResponse("jwt-token");

        when(authService.authenticate(request)).thenReturn(expectedResponse);

        AuthResponse actual = authController.login(request);
        assertEquals("jwt-token", actual.getToken());
    }

    @Test
    void testRegister() {
        AuthRequest request = new AuthRequest("mousam", "password123" , "FARMER");

        when(authService.register(request)).thenReturn("Registered");

        String response = authController.register(request);
        assertEquals("Registered", response);
    }

    @Test
    void testGetMessage() {
        String message = authController.getMessage();
        assertEquals("You have to login with /login", message);
    }
}
