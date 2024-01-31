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

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        verify(auditRepository, times(1)).saveAudit(any());
    }

    @Test
    void testExecute_UsernameAlreadyExists() {
        when(userRepository.isAlreadyExists(any())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> registerUser.execute(validRegisterModel));

        verify(userRepository, never()).save(any());
        verify(auditRepository, never()).saveAudit(any());
    }

    @Test
    void testExecute_InvalidUsernameOrPassword() {
        assertThrows(NotValidUsernameOrPasswordException.class, () -> registerUser.execute(invalidRegisterModel));
        verify(userRepository, never()).save(any());
        verify(auditRepository, never()).saveAudit(any());
    }
}