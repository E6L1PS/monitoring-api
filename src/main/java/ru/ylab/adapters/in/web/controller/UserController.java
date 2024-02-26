package ru.ylab.adapters.in.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ylab.adapters.in.web.dto.LoginDto;
import ru.ylab.adapters.in.web.dto.RegisterDto;
import ru.ylab.adapters.in.web.dto.TokenDto;
import ru.ylab.application.in.RegisterUser;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.infrastructure.security.JwtService;
import ru.ylab.infrastructure.security.UserService;

/**
 * Создан: 18.02.2024.
 *
 * @author Pesternikov Danil
 */
@Tag(name = "UserController", description = "Контроллер для работы с аутентификацией и авторизацией")
@Loggable
@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final RegisterUser registerUser;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody LoginDto loginDto) {
        //TODO Может вынести в отдельный сервис
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.username(),
                        loginDto.password()
                )
        );

        UserDetails userDetails = userService.loadUserByUsername(loginDto.username());
        TokenDto tokenDto = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/reg")
    public ResponseEntity<Long> registerUser(@RequestBody RegisterDto registerDto) {
        Long id = registerUser.execute(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}
