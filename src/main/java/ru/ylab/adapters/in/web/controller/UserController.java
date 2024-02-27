package ru.ylab.adapters.in.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ylab.adapters.in.web.dto.LoginDto;
import ru.ylab.adapters.in.web.dto.RegisterDto;
import ru.ylab.adapters.in.web.dto.TokenDto;
import ru.ylab.application.in.UserService;
import ru.ylab.aspect.annotation.Loggable;

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

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody LoginDto loginDto) {
        TokenDto tokenDto = userService.authenticate(loginDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/reg")
    public ResponseEntity<Long> registerUser(@RequestBody RegisterDto registerDto) {
        Long id = userService.save(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

}
