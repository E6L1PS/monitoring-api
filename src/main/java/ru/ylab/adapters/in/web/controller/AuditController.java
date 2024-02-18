package ru.ylab.adapters.in.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ylab.adapters.in.web.dto.AuditDto;
import ru.ylab.application.in.GetAuditInfo;
import ru.ylab.application.mapper.AuditMapper;
import ru.ylab.domain.model.Audit;

import java.util.List;

/**
 * Создан: 18.02.2024.
 *
 * @author Pesternikov Danil
 */
@RestController
@RequestMapping("audit")
@RequiredArgsConstructor
public class AuditController {

    private final GetAuditInfo getAuditInfo;
    private final AuditMapper auditMapper;

    @GetMapping
    public List<AuditDto> getAll() {
        List<Audit> audits = getAuditInfo.execute();
        List<AuditDto> auditsDto = auditMapper.toListDto(audits);
        return auditsDto;
    }
}
