package ru.ylab.adapters.in.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.ylab.adapters.in.web.dto.LoginDto;
import ru.ylab.adapters.in.web.dto.RegisterDto;
import ru.ylab.adapters.in.web.dto.TokenDto;
import ru.ylab.application.in.RegisterUser;
import ru.ylab.infrastructure.security.JwtService;
import ru.ylab.infrastructure.security.UserService;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Создан: 24.02.2024.
 *
 * @author Pesternikov Danil
 */
@DisplayName("Тест UserController")
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RegisterUser registerUser;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createAuthToken_ReturnsResponseEntity() throws Exception {
        LoginDto loginDto = new LoginDto("username", "password");
        TokenDto tokenDto = new TokenDto("username", new Date(1), new Date(1), List.of("USER"));
        String s = objectMapper.writeValueAsString(loginDto);

        when(userService.loadUserByUsername(loginDto.username())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(tokenDto);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value("username"))
                .andExpect(jsonPath("$.issuedDate").value("1970-01-01 00:00:00"))
                .andExpect(jsonPath("$.expirationTime").value("1970-01-01 00:00:00"))
                .andExpect(jsonPath("$.roles[0]").value("USER"));

        verify(authenticationManager).authenticate(any());
        verify(userService).loadUserByUsername(anyString());
        verify(jwtService).generateToken(userDetails);
    }

    @Test
    void registerUser_ReturnsResponseEntity() throws Exception {
        RegisterDto registerDto = new RegisterDto("username", "password");
        String s = objectMapper.writeValueAsString(registerDto);
        when(registerUser.execute(registerDto)).thenReturn(1L);

        mockMvc.perform(post("/auth/reg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(1L));

        verify(registerUser).execute(any());
    }
}