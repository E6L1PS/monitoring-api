package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.application.in.GetAuditInfo;
import ru.ylab.application.mapper.AuditMapper;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.infrastructure.aspect.annotation.Auditable;
import ru.ylab.infrastructure.aspect.annotation.Loggable;
import ru.ylab.domain.model.Audit;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetAuditInfoImpl implements GetAuditInfo {

    private final AuditRepository auditRepository;

    private final AuditMapper auditMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Audit> execute() {
        List<AuditEntity> auditEntities = auditRepository.findAll();
        return auditMapper.toListDomain(auditEntities);
    }
}
