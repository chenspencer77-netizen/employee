package com.flexwork.user.service;

import com.flexwork.common.user.LoginUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    @DisplayName("Should return user when username is admin")
    void findByUsername_whenAdmin_shouldReturnUser() {
        LoginUser user = userService.findByUsername("admin");
        assertNotNull(user);
        assertEquals("1", user.getUserId());
        assertEquals("admin", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    @DisplayName("Should return null when username does not exist")
    void findByUsername_whenNotFound_shouldReturnNull() {
        LoginUser user = userService.findByUsername("unknown");
        assertNull(user);
    }
}
