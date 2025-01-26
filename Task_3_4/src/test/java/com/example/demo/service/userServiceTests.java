package com.example.demo.service;

import com.example.demo.models.Users;
import com.example.demo.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class userServiceTests {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private userService userService;
    @Test
    void testRegister() {
        Users newUser = new Users();
        newUser.setUsername("testUser");
        newUser.setPassword("password123");
        Users registeredUser = userService.register(newUser);
        assertNotNull(registeredUser);
        assertEquals("testUser", registeredUser.getUsername());
        assertEquals("password123", registeredUser.getPassword());

        Users storedUser = userRepository.findByUsername("testUser").orElse(null);
        assertNotNull(storedUser);
        assertEquals("testUser", storedUser.getUsername());
        assertEquals("password123", storedUser.getPassword());
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
        Users newUser = new Users();
        newUser.setUsername("Piyush");
        newUser.setPassword("123");
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.register(newUser);
        });
        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void testVerify_Success() {
        Users newUser = new Users();
        newUser.setUsername("testUser");
        newUser.setPassword("password123");
        String token = userService.verify(newUser);
        assertNotNull(token);
    }

    @Test
    void testVerify_Failure_InvalidUsername() {
        Users nonExistentUser = new Users();
        nonExistentUser.setUsername("nonExistentUser");
        nonExistentUser.setPassword("password123");
        String result = userService.verify(nonExistentUser);
        assertEquals("Failed: Bad credentials", result);
    }
}

