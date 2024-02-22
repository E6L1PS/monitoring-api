package ru.ylab.adapters.in.web.controller;

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
import ru.ylab.application.in.RegisterUser;
import ru.ylab.application.mapper.UserMapper;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.config.security.JwtService;
import ru.ylab.config.security.UserService;

/**
 * Создан: 18.02.2024.
 *
 * @author Pesternikov Danil
 */
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
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.username(),
                        loginDto.password()
                )
        );

        UserDetails userDetails = userService.loadUserByUsername(loginDto.username());
        String token = jwtService.generateToken(userDetails);
        //TODO DTO jwt token
        return ResponseEntity.ok(token);
    }

    @PostMapping("/reg")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto) {
        Long id = registerUser.execute(userMapper.toDomain(registerDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}
