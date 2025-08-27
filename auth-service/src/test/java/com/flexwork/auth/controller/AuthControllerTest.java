package com.flexwork.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexwork.common.user.LoginUser;
import com.flexwork.common.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Use @MockBean to mock the Dubbo service dependency
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("POST /auth/login - Success")
    void login_whenValidCredentials_shouldReturnToken() throws Exception {
        // Arrange
        LoginUser mockUser = new LoginUser();
        mockUser.setUserId("1");
        mockUser.setUsername("admin");
        mockUser.setPassword("password");
        when(userService.findByUsername("admin")).thenReturn(mockUser);

        LoginRequest loginRequest = new LoginRequest("admin", "password");

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isString());
    }

    @Test
    @DisplayName("POST /auth/login - User Not Found")
    void login_whenUserNotFound_shouldReturnError() throws Exception {
        // Arrange
        when(userService.findByUsername(anyString())).thenReturn(null);
        LoginRequest loginRequest = new LoginRequest("unknown", "password");

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("Invalid username or password"));
    }

    @Test
    @DisplayName("POST /auth/login - Invalid Password")
    void login_whenInvalidPassword_shouldReturnError() throws Exception {
        // Arrange
        LoginUser mockUser = new LoginUser();
        mockUser.setUserId("1");
        mockUser.setUsername("admin");
        mockUser.setPassword("correct_password");
        when(userService.findByUsername("admin")).thenReturn(mockUser);

        LoginRequest loginRequest = new LoginRequest("admin", "wrong_password");

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("Invalid username or password"));
    }
}
