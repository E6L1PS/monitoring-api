package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.model.UtilityMeterModel;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetUtilityMeterImplTest {

    @Mock
    private MeterRepository meterRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private GetUtilityMeterImpl getUtilityMeter;

    private List<UtilityMeterEntity> entityList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        LocalDate date1 = LocalDate.of(2024, 1, 4);
        LocalDate date2 = LocalDate.of(2024, 2, 4);
        entityList = Arrays.asList(
                UtilityMeterEntity.builder().userId(1L).readingsDate(date1).build(),
                UtilityMeterEntity.builder().userId(1L).readingsDate(date1).build(),
                UtilityMeterEntity.builder().userId(1L).readingsDate(date1).build(),
                UtilityMeterEntity.builder().userId(1L).readingsDate(date2).build(),
                UtilityMeterEntity.builder().userId(1L).readingsDate(date2).build(),
                UtilityMeterEntity.builder().userId(1L).readingsDate(date2).build(),
                UtilityMeterEntity.builder().userId(2L).readingsDate(date1).build(),
                UtilityMeterEntity.builder().userId(2L).readingsDate(date1).build(),
                UtilityMeterEntity.builder().userId(2L).readingsDate(date1).build(),
                UtilityMeterEntity.builder().userId(2L).readingsDate(date2).build(),
                UtilityMeterEntity.builder().userId(2L).readingsDate(date2).build(),
                UtilityMeterEntity.builder().userId(2L).readingsDate(date2).build()
        );
    }

    @Test
    void testExecute() {
        Long userId = 1L;
        var date = entityList.stream()
                .min(Comparator.comparing(UtilityMeterEntity::getReadingsDate))
                .get()
                .getReadingsDate();
        var list = entityList.stream()
                .filter(meter -> meter.getUserId().equals(userId) && meter.getReadingsDate() == date)
                .collect(Collectors.toList());
        when(userRepository.getCurrentUserId()).thenReturn(userId);
        when(meterRepository.findLastByUserId(userId)).thenReturn(list);

        List<UtilityMeterModel> result = getUtilityMeter.execute();

        verify(auditRepository, times(1)).save(any());
        assertThat(result).hasSize(3);
    }
}