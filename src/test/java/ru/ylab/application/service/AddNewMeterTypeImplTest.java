package ru.ylab.application.service;

import com.github.dockerjava.api.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.adapters.out.persistence.repository.AuditRepositoryImpl;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.aspect.AuditAspect;
import ru.ylab.domain.model.MeterType;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddNewMeterTypeImplTest {

    @Mock
    MeterTypeRepository meterTypeRepository;

    @Mock
    AuditRepositoryImpl auditRepository;

    @InjectMocks
    AuditAspect auditAspect;

    @InjectMocks
    AddNewMeterTypeImpl addNewMeterType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteWithAdminRole() throws Exception {
        var type = MeterType.builder().name("Электричество").build();

        addNewMeterType.execute(type);

        verify(meterTypeRepository, times(1)).save(any());
        verify(auditRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Тест выполнения с ролью USER")
    void testExecuteWithNonAdminRole() {
        var type = MeterType.builder().name("Электричество").build();

        assertThatThrownBy(() -> addNewMeterType.execute(type))
                .isInstanceOf(UnauthorizedException.class);

        verify(meterTypeRepository, never()).save(any());
    }
}