package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.application.exception.NotValidUsernameOrPasswordException;
import ru.ylab.application.exception.UsernameAlreadyExistsException;
import ru.ylab.application.model.RegisterModel;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.UserRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisterUserImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private RegisterUserImpl registerUser;

    private RegisterModel validRegisterModel;

    private RegisterModel invalidRegisterModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validRegisterModel = new RegisterModel("testUser", "password");
        invalidRegisterModel = new RegisterModel("21", "password");
    }

    @Test
    void testExecute_SuccessfulRegistration() {
        when(userRepository.isAlreadyExists(any())).thenReturn(false);

        registerUser.execute(validRegisterModel);

        verify(userRepository, times(1)).save(any());
        verify(auditRepository, times(1)).save(any());
    }

    @Test
    void testExecute_UsernameAlreadyExists() {
        when(userRepository.isAlreadyExists(any())).thenReturn(true);

        assertThatThrownBy(() -> registerUser.execute(validRegisterModel))
                .isInstanceOf(UsernameAlreadyExistsException.class);

        verify(userRepository, never()).save(any());
        verify(auditRepository, never()).save(any());
    }

    @Test
    void testExecute_InvalidUsernameOrPassword() {
        assertThatThrownBy(() -> registerUser.execute(invalidRegisterModel))
                .isInstanceOf(NotValidUsernameOrPasswordException.class);

        verify(userRepository, never()).save(any());
        verify(auditRepository, never()).save(any());
    }
}