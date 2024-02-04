package ru.ylab.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.application.exception.MonthlySubmitLimitException;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.application.out.UserRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SubmitUtilityMeterImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private MeterRepository meterRepository;

    @Mock
    private MeterTypeRepository meterTypeRepository;

    @InjectMocks
    private SubmitUtilityMeterImpl submitUtilityMeter;

    private Map<String, Double> utilityMeters;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        utilityMeters = new HashMap<>();
        utilityMeters.put("Холодная вода", 100.0);
    }

    @Test
    @DisplayName("Если в этом месяце еще не подавали показания")
    void testExecuteWhenMonthlyLimitNotExceeded() {
        int month = LocalDate.now().getMonthValue();
        Long userId = 1L;
        when(userRepository.getCurrentUserId()).thenReturn(userId);
        when(meterTypeRepository.isMeterTypeExists(any())).thenReturn(true);
        when(meterRepository.findByMonthAndUserId(month, userId)).thenReturn(Collections.emptyList());

        submitUtilityMeter.execute(utilityMeters);

        verify(meterRepository, times(1)).save(any());
        verify(auditRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Если в этом месяце уже подавали показания")
    void testExecuteWhenMonthlyLimitExceeded() {
        when(meterRepository.isSubmitted(any())).thenReturn(true);

        Assertions.assertThatThrownBy(() -> submitUtilityMeter.execute(utilityMeters))
                .isInstanceOf(MonthlySubmitLimitException.class);
    }
}