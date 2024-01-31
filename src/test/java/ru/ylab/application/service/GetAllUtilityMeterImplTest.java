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
import ru.ylab.domain.model.Role;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetAllUtilityMeterImplTest {

    @Mock
    private MeterRepository meterRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private GetAllUtilityMeterImpl getAllUtilityMeter;

    private List<UtilityMeterEntity> entityList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entityList = Arrays.asList(
                UtilityMeterEntity.builder().username("username1").build(),
                UtilityMeterEntity.builder().username("username1").build(),
                UtilityMeterEntity.builder().username("username1").build(),
                UtilityMeterEntity.builder().username("username2").build(),
                UtilityMeterEntity.builder().username("username2").build(),
                UtilityMeterEntity.builder().username("username2").build()
        );
    }

    @Test
    void testExecuteWithAdminRole() {
        when(userRepository.getCurrentRoleUser()).thenReturn(Role.ADMIN);
        when(meterRepository.findAll()).thenReturn(entityList);

        List<UtilityMeterModel> result = getAllUtilityMeter.execute();

        verify(auditRepository, times(1)).saveAudit(any());
        assertEquals(entityList.size(), result.size());
    }

    @Test
    void testExecuteWithUserRole() {
        when(userRepository.getCurrentRoleUser()).thenReturn(Role.USER);
        String username = "username1";
        when(userRepository.getCurrentUsername()).thenReturn(username);
        when(meterRepository.findAllByUsername(username)).thenReturn(entityList.subList(0, 3));

        List<UtilityMeterModel> result = getAllUtilityMeter.execute();

        verify(auditRepository, times(1)).saveAudit(any());
        assertEquals(3, result.size());
    }

}