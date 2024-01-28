package ru.ylab.application.out;

import ru.ylab.adapters.out.persistence.entity.AuditEntity;

import java.util.List;

public interface AuditRepository {

    List<AuditEntity> findAll();
    void saveAudit(AuditEntity auditEntity);
}
