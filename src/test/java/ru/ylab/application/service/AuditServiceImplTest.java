package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.ylab.adapters.in.web.dto.AuditDto;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.application.mapper.AuditMapper;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.domain.model.Audit;
import ru.ylab.domain.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

/**
 * Создан: 28.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {

    private static List<AuditDto> auditsDto;

    private static List<Audit> audits;

    private static List<AuditEntity> auditEntities;

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private AuditMapper auditMapper;

    @InjectMocks
    private AuditServiceImpl auditService;


    @BeforeAll
    static void setUp() {
        AuditMapper mapper = Mappers.getMapper(AuditMapper.class);
        auditEntities = List.of(AuditEntity.builder().build());
        audits = mapper.toListDomain(auditEntities);
        auditsDto = mapper.toListDto(audits);
    }

    @Test
    void getAll_Success() {
        when(auditRepository.findAll()).thenReturn(auditEntities);
        when(auditMapper.toListDomain(anyList())).thenReturn(audits);
        when(auditMapper.toListDto(anyList())).thenReturn(auditsDto);

        List<AuditDto> auditsDto = auditService.getAll();

        assertThat(auditsDto).hasSize(1);
        assertThat(auditsDto).isNotNull();
        verify(auditRepository).findAll();
    }

    @Test
    void save_Success() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);
        User user = User.builder().id(1L).build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);

        auditService.save("UserServiceImpl.save");

        verify(auditRepository).save(any(AuditEntity.class));
    }

}