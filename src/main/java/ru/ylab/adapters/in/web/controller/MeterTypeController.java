package ru.ylab.adapters.in.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ylab.adapters.in.web.dto.MeterTypeDto;
import ru.ylab.application.in.AddNewMeterType;
import ru.ylab.application.in.GetUtilityMeterTypes;
import ru.ylab.application.mapper.MeterTypeMapper;
import ru.ylab.infrastructure.aspect.annotation.Loggable;
import ru.ylab.domain.model.MeterType;

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

    private final MeterTypeMapper meterTypeMapper;

    @GetMapping
    public ResponseEntity<List<MeterTypeDto>> getAll() {
        List<MeterType> meterTypes = getUtilityMeterTypes.execute();
        List<MeterTypeDto> meterTypesDto = meterTypeMapper.toListDto(meterTypes);
        return ResponseEntity.ok(meterTypesDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody MeterType meterType) {
        addNewMeterType.execute(meterType);
        return ResponseEntity.status(HttpStatus.CREATED).body(meterType);
    }
}
