package ru.ylab.adapters.in.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ylab.adapters.in.web.dto.MeterTypeDto;
import ru.ylab.application.in.AddNewMeterType;
import ru.ylab.application.in.GetUtilityMeterTypes;
import ru.ylab.infrastructure.aspect.annotation.Loggable;

import java.util.List;

/**
 * Создан: 18.02.2024.
 *
 * @author Pesternikov Danil
 */
@Loggable
@RestController
@RequestMapping("type")
@RequiredArgsConstructor
public class MeterTypeController {

    private final GetUtilityMeterTypes getUtilityMeterTypes;

    private final AddNewMeterType addNewMeterType;

    @GetMapping
    public ResponseEntity<List<MeterTypeDto>> getAll() {
        List<MeterTypeDto> meterTypesDto = getUtilityMeterTypes.execute();
        return ResponseEntity.ok(meterTypesDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody MeterTypeDto meterTypeDto) {
        addNewMeterType.execute(meterTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(meterTypeDto);
    }
}
