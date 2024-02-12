package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.exception.IncorrectPasswordException;
import ru.ylab.application.exception.UserNotFoundException;
import ru.ylab.adapters.in.web.dto.LoginModel;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.Role;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class LoginUserImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private LoginUserImpl loginUser;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = UserEntity.builder().username("testUser").password("password").role(Role.USER).build();
    }

    @Test
    public void testExecute_ValidUserAndPassword() {
        LoginModel loginModel = new LoginModel("testUser", "password");
        when(userRepository.getByUsername(anyString())).thenReturn(user);

        loginUser.execute(loginModel);

        verify(auditRepository, times(1)).save(any());
    }

    @Test
    public void testExecute_InvalidPassword() {
        LoginModel loginModel = new LoginModel("testUser", "wrongPassword");
        when(userRepository.getByUsername(anyString())).thenReturn(user);

        assertThatThrownBy(() -> loginUser.execute(loginModel))
                .isInstanceOf(IncorrectPasswordException.class);

        verify(auditRepository, never()).save(any());
    }

    @Test
    public void testExecute_UserNotFound() {
        LoginModel loginModel = new LoginModel("nonExistentUser", "password");
        when(userRepository.getByUsername(anyString())).thenReturn(null);

        assertThatThrownBy(() -> loginUser.execute(loginModel))
                .isInstanceOf(UserNotFoundException.class);

        verify(auditRepository, never()).save(any());
    }
}