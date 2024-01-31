package ru.ylab.application.in;

import ru.ylab.adapters.out.persistence.entity.AuditEntity;

import java.util.List;

public interface GetAuditInfo {

    List<AuditEntity> execute();
}
