package ru.ylab.adapters.in.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ylab.adapters.in.web.dto.MeterTypeDto;
import ru.ylab.application.in.MeterTypeService;
import ru.ylab.aspect.annotation.Loggable;

import java.util.List;

/**
 * Создан: 18.02.2024.
 *
 * @author Pesternikov Danil
 */
@Tag(name = "MeterTypeController", description = "Контроллер для работы с типом счетчиков")
@Loggable
@RestController
@RequestMapping("type")
@RequiredArgsConstructor
public class MeterTypeController {

    private final MeterTypeService meterTypeService;

    @GetMapping
    public ResponseEntity<List<MeterTypeDto>> getAll() {
        List<MeterTypeDto> meterTypesDto = meterTypeService.getAll();
        return ResponseEntity.ok(meterTypesDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody MeterTypeDto meterTypeDto) {
        meterTypeService.save(meterTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(meterTypeDto);
    }

}
