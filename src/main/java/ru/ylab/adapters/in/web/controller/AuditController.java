package ru.ylab.adapters.in.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ylab.adapters.in.web.dto.AuditDto;
import ru.ylab.application.in.AuditService;
import ru.ylab.aspect.annotation.Loggable;

import java.util.List;

/**
 * Создан: 18.02.2024.
 *
 * @author Pesternikov Danil
 */
@Tag(name = "AuditController", description = "Контроллер для работы с аудитом")
@Loggable
@RestController
@RequestMapping("audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public ResponseEntity<List<AuditDto>> getAll() {
        List<AuditDto> auditsDto = auditService.getAll();
        return ResponseEntity.ok(auditsDto);
    }

}
