package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.out.AuditRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@NoArgsConstructor
public class AuditRepositoryImpl implements AuditRepository {

    private final List<AuditEntity> logs = new ArrayList<>();

    @Override
    public List<AuditEntity> findAll() {
        return logs.stream()
                .sorted(Comparator.comparing(AuditEntity::getDateTime))
                .collect(Collectors.toList());
    }

    @Override
    public void saveAudit(AuditEntity auditEntity) {
        logs.add(auditEntity);
    }
}
