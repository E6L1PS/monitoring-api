package ru.ylab.application.mapper;

import org.mapstruct.Mapper;
import ru.ylab.adapters.in.web.dto.AuditDto;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.domain.model.Audit;

import java.util.List;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@Mapper(componentModel = "spring")
public interface AuditMapper {

    Audit toDomain(AuditEntity auditEntity);

    AuditDto toDto(AuditEntity auditEntity);

    List<Audit> toListDomain(List<AuditEntity> auditEntities);

    List<AuditDto> toListDto(List<Audit> audits);

}
