package ru.ylab.adapters.in.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import ru.ylab.adapters.in.web.dto.LoginDto;
import ru.ylab.adapters.in.web.dto.RegisterDto;
import ru.ylab.adapters.in.web.dto.TokenDto;
import ru.ylab.application.in.RegisterUser;
import ru.ylab.infrastructure.security.JwtService;
import ru.ylab.infrastructure.security.UserService;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Создан: 24.02.2024.
 *
 * @author Pesternikov Danil
 */
@DisplayName("Тест UserController")
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    RegisterUser registerUser;

    @Mock
    JwtService jwtService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserDetails userDetails;

    @InjectMocks
    UserController userController;

    @Test
    void createAuthToken_ReturnsResponseEntity() {
        LoginDto loginDto = new LoginDto("username", "password");
        TokenDto tokenDto = new TokenDto("username", new Date(1), new Date(1), List.of("USER"));
        when(userService.loadUserByUsername(loginDto.username())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(tokenDto);

        var authToken = userController.createAuthToken(loginDto);

        assertThat(authToken).isNotNull();
        assertThat(authToken.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authToken.getBody()).isEqualTo(tokenDto);
    }

    @Test
    void registerUser_ReturnsResponseEntity() {
        RegisterDto registerDto = new RegisterDto("username", "password");
        when(registerUser.execute(registerDto)).thenReturn(1L);

        var authToken = userController.registerUser(registerDto);

        assertThat(authToken).isNotNull();
        assertThat(authToken.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(authToken.getBody()).isEqualTo(1L);
    }
}