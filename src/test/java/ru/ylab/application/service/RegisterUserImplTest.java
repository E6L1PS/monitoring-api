package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ylab.adapters.in.web.dto.RegisterDto;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.exception.NotValidUsernameOrPasswordException;
import ru.ylab.application.exception.UsernameAlreadyExistsException;
import ru.ylab.application.mapper.UserMapper;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserImplTest {

    private static RegisterDto validRegisterDto;

    private static User userValid;

    private static UserEntity userEntityValid;

    private static RegisterDto invalidRegisterDto;

    private static User userInvalid;

    private static UserEntity userEntityInvalid;

    @Mock
    private UserRepository userRepository;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private RegisterUserImpl registerUser;

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
    void execute_Success() {
        when(userMapper.toDomain(any(RegisterDto.class))).thenReturn(userValid);
        when(userRepository.isAlreadyExists(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("test");

        registerUser.execute(validRegisterDto);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void execute_ThrowsUsernameAlreadyExistsException() {
        when(userMapper.toDomain(any(RegisterDto.class))).thenReturn(userValid);
        when(userRepository.isAlreadyExists(any())).thenReturn(true);

        assertThatThrownBy(() -> registerUser.execute(validRegisterDto))
                .isInstanceOf(UsernameAlreadyExistsException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    void execute_ThrowsInvalidUsernameOrPasswordException() {
        when(userMapper.toDomain(any(RegisterDto.class))).thenReturn(userInvalid);

        assertThatThrownBy(() -> registerUser.execute(invalidRegisterDto))
                .isInstanceOf(NotValidUsernameOrPasswordException.class);

        verify(userRepository, never()).save(any());
    }

}