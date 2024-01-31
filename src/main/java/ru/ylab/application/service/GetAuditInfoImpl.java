package ru.ylab.application.service;

import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.GetAuditInfo;
import ru.ylab.application.out.AuditRepository;

import java.util.List;

@Singleton
public class GetAuditInfoImpl implements GetAuditInfo {

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public List<AuditEntity> execute() {
        return auditRepository.findAll();
    }
}
