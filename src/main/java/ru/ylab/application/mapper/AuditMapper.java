package ru.ylab.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.application.model.AuditModel;

import java.util.List;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@Mapper
public interface AuditMapper {

    AuditMapper INSTANCE = Mappers.getMapper(AuditMapper.class);

    AuditModel toAuditModel(AuditEntity auditEntity);

    List<AuditModel> toListAuditModel(List<AuditEntity> auditEntities);
}
