package com.example.pocapi.controller;

import com.example.pocapi.dto.AuthRequest;
import com.example.pocapi.dto.AuthResponse;
import com.example.pocapi.security.JwtTokenProvider;
import com.example.pocapi.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void authenticateUserReturnsJwtWhenValidCredentials() throws Exception {
        AuthRequest authRequest = new AuthRequest("testuser", "password123");
        String jwtToken = "mock-jwt-token";

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken("testuser", "password123");
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(mockAuthentication);
        when(jwtTokenProvider.generateToken(any(Authentication.class))).thenReturn(jwtToken);

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(jwtToken));
    }

    @Test
    void authenticateUserReturnsBadRequestWhenUsernameIsBlank() throws Exception {
        AuthRequest authRequest = new AuthRequest("", "password123");

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authenticateUserReturnsBadRequestWhenUsernameTooShort() throws Exception {
        AuthRequest authRequest = new AuthRequest("ab", "password123");

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authenticateUserReturnsBadRequestWhenPasswordIsBlank() throws Exception {
        AuthRequest authRequest = new AuthRequest("testuser", "");

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authenticateUserReturnsBadRequestWhenPasswordTooShort() throws Exception {
        AuthRequest authRequest = new AuthRequest("testuser", "123");

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest());
    }
}
