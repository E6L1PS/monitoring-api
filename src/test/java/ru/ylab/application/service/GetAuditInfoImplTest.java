package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.adapters.in.web.dto.AuditDto;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.application.mapper.AuditMapper;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.domain.model.Audit;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Создан: 23.02.2024.
 *
 * @author Pesternikov Danil
 */

@ExtendWith(MockitoExtension.class)
class GetAuditInfoImplTest {

    private static List<AuditDto> auditsDto;

    private static List<Audit> audits;

    private static List<AuditEntity> auditEntities;

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private AuditMapper auditMapper;

    @InjectMocks
    private GetAuditInfoImpl getAuditInfo;

    @BeforeAll
    static void setUp() {
        AuditMapper mapper = Mappers.getMapper(AuditMapper.class);
        auditEntities = List.of(AuditEntity.builder().build());
        audits = mapper.toListDomain(auditEntities);
        auditsDto = mapper.toListDto(audits);
    }

    @Test
    void execute_Success() {
        when(auditRepository.findAll()).thenReturn(auditEntities);
        when(auditMapper.toListDomain(anyList())).thenReturn(audits);
        when(auditMapper.toListDto(anyList())).thenReturn(auditsDto);

        List<AuditDto> auditsDto = getAuditInfo.execute();

        assertThat(auditsDto).hasSize(1);
        assertThat(auditsDto).isNotNull();
        verify(auditRepository).findAll();
    }

}