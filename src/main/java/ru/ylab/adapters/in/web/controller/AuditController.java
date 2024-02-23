package ru.ylab.adapters.in.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ylab.adapters.in.web.dto.AuditDto;
import ru.ylab.application.in.GetAuditInfo;
import ru.ylab.application.mapper.AuditMapper;
import ru.ylab.infrastructure.aspect.annotation.Loggable;
import ru.ylab.domain.model.Audit;

import java.util.List;

/**
 * Создан: 18.02.2024.
 *
 * @author Pesternikov Danil
 */
@Loggable
@RestController
@RequestMapping("audit")
@Tag(name = "", description = "")
@RequiredArgsConstructor
public class AuditController {

    private final GetAuditInfo getAuditInfo;

    private final AuditMapper auditMapper;


    @Operation(
            summary = "",
            description = ""
    )
    @ApiResponse(
            responseCode = "200",
            description = ""
                )
    @GetMapping
    public ResponseEntity<List<AuditDto>> getAll() {
        List<Audit> audits = getAuditInfo.execute();
        List<AuditDto> auditsDto = auditMapper.toListDto(audits);
        return ResponseEntity.ok(auditsDto);
    }
}
