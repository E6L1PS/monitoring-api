package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.in.web.dto.AuditDto;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.application.in.GetAuditInfo;
import ru.ylab.application.mapper.AuditMapper;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.Audit;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Loggable
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GetAuditInfoImpl implements GetAuditInfo {

    private final AuditRepository auditRepository;

    private final AuditMapper auditMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuditDto> execute() {
        List<AuditEntity> auditEntities = auditRepository.findAll();
        List<Audit> audits = auditMapper.toListDomain(auditEntities);
        //internal business logic with domain model if needed
        List<AuditDto> auditsDto = auditMapper.toListDto(audits);
        return auditsDto;
    }
}
