package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.out.AuditRepository;

import java.util.ArrayList;
import java.util.List;

@Singleton
@NoArgsConstructor
public class AuditRepositoryImpl implements AuditRepository {

    private final List<AuditEntity> logs = new ArrayList<>();

    @Override
    public List<AuditEntity> findAll() {
        return logs;
    }

    @Override
    public void saveAudit(AuditEntity auditEntity) {
        logs.add(auditEntity);
    }
}
