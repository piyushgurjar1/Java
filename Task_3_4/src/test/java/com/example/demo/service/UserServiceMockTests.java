package com.example.demo.service;

import com.example.demo.models.Users;
import com.example.demo.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceMockTests {

    @Mock
    private UserRepo userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private userService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        Users newUser = new Users();
        newUser.setUsername("testUser");
        newUser.setPassword("password123");
        when(userRepository.findByUsername("testUser")).thenReturn(java.util.Optional.empty());
        when(userRepository.save(any(Users.class))).thenReturn(newUser);
        Users registeredUser = userService.register(newUser);
        assertNotNull(registeredUser);
        assertEquals("testUser", registeredUser.getUsername());
        assertEquals("password123", registeredUser.getPassword());

        verify(userRepository, times(1)).findByUsername("testUser");
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testRegister_InvalidUser() {
        Users invalidUser = new Users();
        invalidUser.setUsername("");
        invalidUser.setPassword("password123");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.register(invalidUser);
        });
        assertEquals("Username cannot be empty", exception.getMessage());
    }

    @Test
    void testRegister_UserAlreadyExists() {
        Users existingUser = new Users();
        existingUser.setUsername("Piyush");
        existingUser.setPassword("123");

        when(userRepository.findByUsername("Piyush")).thenReturn(java.util.Optional.of(existingUser));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.register(existingUser);
        });
        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("Piyush");
    }


    @Test
    void testVerify_Failure_InvalidCredentials() {
        Users invalidUser = new Users();
        invalidUser.setUsername("testUser");
        invalidUser.setPassword("wrongPassword");
        when(userRepository.findByUsername("testUser")).thenReturn(java.util.Optional.of(invalidUser));
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new RuntimeException("Authentication failed"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.verify(invalidUser);
        });
        assertEquals("Authentication failed", exception.getMessage());
    }
}
