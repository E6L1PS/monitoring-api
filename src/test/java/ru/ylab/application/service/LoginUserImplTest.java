package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.exception.IncorrectPasswordException;
import ru.ylab.application.exception.UserNotFoundException;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.Role;
import ru.ylab.domain.model.User;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class LoginUserImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private LoginUserImpl loginUser;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userEntity = UserEntity.builder().username("testUser").password("password").role(Role.USER).build();
    }

    @Test
    public void testExecute_ValidUserAndPassword() {
        var user = User.builder().username("testUser").password("password").build();
        when(userRepository.getByUsername(anyString())).thenReturn(userEntity);

        loginUser.execute(user);

        verify(auditRepository, times(1)).save(any());
    }

    @Test
    public void testExecute_InvalidPassword() {
        var user = User.builder().username("testUser").password("wrongPassword").build();
        when(userRepository.getByUsername(anyString())).thenReturn(userEntity);

        assertThatThrownBy(() -> loginUser.execute(user))
                .isInstanceOf(IncorrectPasswordException.class);

        verify(auditRepository, never()).save(any());
    }

    @Test
    public void testExecute_UserNotFound() {
        var user = User.builder().username("nonExistentUser").password("password").build();
        when(userRepository.getByUsername(anyString())).thenReturn(null);

        assertThatThrownBy(() -> loginUser.execute(user))
                .isInstanceOf(UserNotFoundException.class);

        verify(auditRepository, never()).save(any());
    }
}