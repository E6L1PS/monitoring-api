package ru.ylab.adapters.in.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ylab.adapters.in.web.dto.AuditDto;
import ru.ylab.application.in.GetAuditInfo;
import ru.ylab.infrastructure.aspect.annotation.Loggable;

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

    private final GetAuditInfo getAuditInfo;

    @Operation(
            summary = "Получить всю информацию об аудите",
            description = "Получить список всех аудитов."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Успешное получение списка аудитов.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuditDto.class)
            )
    )
    @GetMapping
    public ResponseEntity<List<AuditDto>> getAll() {
        List<AuditDto> auditsDto = getAuditInfo.execute();
        return ResponseEntity.ok(auditsDto);
    }
}
