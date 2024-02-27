package ru.ylab.application.in;

import ru.ylab.adapters.in.web.dto.LoginDto;
import ru.ylab.adapters.in.web.dto.RegisterDto;
import ru.ylab.adapters.in.web.dto.TokenDto;

/**
 * Создан: 27.02.2024.
 *
 * @author Pesternikov Danil
 */
public interface UserService {

    Long save(RegisterDto registerDto);

    TokenDto authenticate(LoginDto loginDto);

}
