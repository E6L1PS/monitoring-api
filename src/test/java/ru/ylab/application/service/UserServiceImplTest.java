package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ylab.adapters.in.web.dto.LoginDto;
import ru.ylab.adapters.in.web.dto.RegisterDto;
import ru.ylab.adapters.in.web.dto.TokenDto;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.exception.NotValidUsernameOrPasswordException;
import ru.ylab.application.exception.UsernameAlreadyExistsException;
import ru.ylab.application.mapper.UserMapper;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.User;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Создан: 28.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static RegisterDto validRegisterDto;
    private static RegisterDto invalidRegisterDto;

    private static User userValid;
    private static User userInvalid;

    private static UserEntity userEntityValid;
    private static UserEntity userEntityInvalid;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserDetails userDetails;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeAll
    static void setUp() {
        UserMapper mapper = Mappers.getMapper(UserMapper.class);

        validRegisterDto = new RegisterDto("testUser", "password");
        userValid = mapper.toDomain(validRegisterDto);
        userEntityValid = mapper.toEntity(userValid);

        invalidRegisterDto = new RegisterDto("21", "password");
        userInvalid = mapper.toDomain(invalidRegisterDto);
        userEntityInvalid = mapper.toEntity(userInvalid);
    }

    @Test
    void authenticate() {
        LoginDto loginDto = new LoginDto("username", "password");
        TokenDto tokenDto = new TokenDto("username", new Date(1), new Date(1), List.of("USER"));
        when(userDetailsService.loadUserByUsername(loginDto.username())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(tokenDto);

        TokenDto expected = userService.authenticate(loginDto);

        assertThat(tokenDto).isNotNull();
        assertThat(tokenDto).isEqualTo(expected);
        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken(userDetails);
    }

    @Test
    void save() {
        when(userMapper.toDomain(any(RegisterDto.class))).thenReturn(userValid);
        when(userRepository.isAlreadyExists(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("test");

        userService.save(validRegisterDto);

        verify(userRepository, times(1)).save(any());
    }


    @Test
    void save_ThrowsUsernameAlreadyExistsException() {
        when(userMapper.toDomain(any(RegisterDto.class))).thenReturn(userValid);
        when(userRepository.isAlreadyExists(any())).thenReturn(true);

        assertThatThrownBy(() -> userService.save(validRegisterDto))
                .isInstanceOf(UsernameAlreadyExistsException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    void save_ThrowsInvalidUsernameOrPasswordException() {
        when(userMapper.toDomain(any(RegisterDto.class))).thenReturn(userInvalid);

        assertThatThrownBy(() -> userService.save(invalidRegisterDto))
                .isInstanceOf(NotValidUsernameOrPasswordException.class);

        verify(userRepository, never()).save(any());
    }
}