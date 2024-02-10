package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.application.exception.UnauthorizedException;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.Role;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddNewMeterTypeImplTest {

    @Mock
    private MeterTypeRepository meterTypeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private AddNewMeterTypeImpl addNewMeterType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Тест выполнения с ролью ADMIN")
    void testExecuteWithAdminRole() {
        when(userRepository.getCurrentRoleUser()).thenReturn(Role.ADMIN);
        String meterTypeName = "Электричество";

        addNewMeterType.execute(meterTypeName);

        verify(meterTypeRepository, times(1)).save(any());
        verify(auditRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Тест выполнения с ролью USER")
    void testExecuteWithNonAdminRole() {
        when(userRepository.getCurrentRoleUser()).thenReturn(Role.USER);
        String meterTypeName = "Электричество";

        assertThatThrownBy(() -> addNewMeterType.execute(meterTypeName))
                .isInstanceOf(UnauthorizedException.class);

        verify(meterTypeRepository, never()).save(any());
        verify(auditRepository, never()).save(any());
    }
}