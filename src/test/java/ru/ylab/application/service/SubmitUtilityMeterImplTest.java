package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.application.out.UserRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

    private List<MeterRepository> meterRepositoryList;

    private Map<String, Double> utilityMeters;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        utilityMeters = new HashMap<>();
        utilityMeters.put("Холодная вода", 100.0);
    }

    @Test
    void testExecuteWhenMonthlyLimitNotExceeded() {
        String username = "testUser";
        when(userRepository.getCurrentUsername()).thenReturn(username);
        when(meterTypeRepository.isValid(any())).thenReturn(true);
        when(meterRepository.findByMonth(anyInt())).thenReturn(Collections.emptyList());

        submitUtilityMeter.execute(utilityMeters);

        verify(meterRepository, times(1)).create(any());
        verify(auditRepository, times(1)).saveAudit(any());
    }

}