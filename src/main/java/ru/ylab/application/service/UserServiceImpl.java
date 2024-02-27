package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.in.web.dto.LoginDto;
import ru.ylab.adapters.in.web.dto.RegisterDto;
import ru.ylab.adapters.in.web.dto.TokenDto;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.exception.NotValidUsernameOrPasswordException;
import ru.ylab.application.exception.UsernameAlreadyExistsException;
import ru.ylab.application.in.UserService;
import ru.ylab.application.mapper.UserMapper;
import ru.ylab.application.out.UserRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.User;

/**
 * Создан: 27.02.2024.
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Long save(RegisterDto registerDto) {
        User user = userMapper.toDomain(registerDto);

        if (userRepository.isAlreadyExists(user.getUsername())) {
            throw new UsernameAlreadyExistsException(
                    "Пользователь с именем: '" + user.getUsername() + "' уже существует!"
            );
        }

        if (!(user.usernameIsValid() && user.passwordIsValid())) {
            throw new NotValidUsernameOrPasswordException("Not Valid Username Or Password!");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        UserEntity userEntity = userMapper.toEntity(user);
        return userRepository.save(userEntity);
    }

    @Override
    public TokenDto authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.username(),
                        loginDto.password()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.username());
        TokenDto tokenDto = jwtService.generateToken(userDetails);
        return tokenDto;
    }

}
