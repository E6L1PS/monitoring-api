package ru.ylab.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.domain.model.User;

import static org.mockito.Mockito.*;

/**
 * Создан: 27.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class AddAuditInfoImplTest {

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private AddAuditInfoImpl addAuditInfo;

    @Test
    void execute_Success() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);
        User user = User.builder().id(1L).build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);

        addAuditInfo.execute("RegisterUserImpl");

        verify(auditRepository).save(any(AuditEntity.class));
    }

}