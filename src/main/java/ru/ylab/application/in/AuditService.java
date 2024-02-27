package ru.ylab.application.in;

import ru.ylab.adapters.in.web.dto.AuditDto;

import java.util.List;

/**
 * Создан: 27.02.2024.
 *
 * @author Pesternikov Danil
 */
public interface AuditService {

    List<AuditDto> getAll();

    void save(String signature);

}
