package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.adapters.in.web.dto.UtilityMeterModel;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.Role;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetAllUtilityMeterImplTest {

    @Mock
    private MeterRepository meterRepository;

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private GetAllUtilityMeterImpl getAllUtilityMeter;

    private List<UtilityMeterEntity> entityList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entityList = Arrays.asList(
                UtilityMeterEntity.builder().userId(1L).build(),
                UtilityMeterEntity.builder().userId(1L).build(),
                UtilityMeterEntity.builder().userId(1L).build(),
                UtilityMeterEntity.builder().userId(1L).build(),
                UtilityMeterEntity.builder().userId(1L).build(),
                UtilityMeterEntity.builder().userId(1L).build()
        );
    }

    @Test
    @DisplayName("Тест выполнения с ролью ADMIN")
    void testExecuteWithAdminRole() {
        when(meterRepository.findAll()).thenReturn(entityList);

        List<UtilityMeterModel> result = getAllUtilityMeter.execute();

        verify(auditRepository, times(1)).save(any());
        assertThat(entityList).hasSize(result.size());
    }

    @Test
    @DisplayName("Тест выполнения с ролью USER")
    void testExecuteWithUserRole() {
        Long userId = 1L;
        when(meterRepository.findAllByUserId(userId)).thenReturn(entityList.subList(0, 3));

        List<UtilityMeterModel> result = getAllUtilityMeter.execute();

        verify(auditRepository, times(1)).save(any());
        assertThat(result).hasSize(3);
    }

}