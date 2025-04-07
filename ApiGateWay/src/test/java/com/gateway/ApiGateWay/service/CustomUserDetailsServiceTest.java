package com.gateway.ApiGateWay.service;

import com.gateway.ApiGateWay.model.User;
import com.gateway.ApiGateWay.ropo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); // Deprecated, consider using openMocks if possible
    }

    @Test
    void loadUserByUsernameTest() {
        // Create a real User object using constructor
        User user = new User(1L, "mousam", "ijfdijfi", "FARMER");

        // Mock the repository
        when(userRepository.findByUsername("mousam")).thenReturn(Optional.of(user));

        // Call the service
        UserDetails userDetails = userDetailsService.loadUserByUsername("mousam");

        // Validate the result
        assertNotNull(userDetails);
        assertEquals("mousam", userDetails.getUsername());
        assertEquals("ijfdijfi", userDetails.getPassword());
    }
}
