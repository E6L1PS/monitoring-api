package ru.ylab.adapters.in.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.application.in.*;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.User;

import java.util.List;
import java.util.Map;

/**
 * Создан: 18.02.2024.
 *
 * @author Pesternikov Danil
 */
@Tag(name = "MeterController", description = "Контроллер для работы с счетчиками")
@Slf4j
@Loggable
@RestController
@RequestMapping("meter")
@RequiredArgsConstructor
public class MeterController {

    private final GetAllUtilityMeter getAllUtilityMeter;

    private final GetAllUtilityMeterById getAllUtilityMeterById;

    private final GetUtilityMeterByMonth getUtilityMeterByMonth;

    private final GetLastUtilityMeter getLastUtilityMeter;

    private final SubmitUtilityMeter submitUtilityMeter;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UtilityMeterDto>> getAll() {
        List<UtilityMeterDto> utilityMetersDto = getAllUtilityMeter.execute();
        return ResponseEntity.ok(utilityMetersDto);
    }

    @PreAuthorize("hasAnyAuthority({'USER', 'ADMIN'})")
    @GetMapping
    public ResponseEntity<List<UtilityMeterDto>> getAllById(@AuthenticationPrincipal User user) {
        List<UtilityMeterDto> utilityMetersDto = getAllUtilityMeterById.execute(user.getId());
        return ResponseEntity.ok(utilityMetersDto);
    }

    @PreAuthorize("hasAnyAuthority({'USER', 'ADMIN'})")
    @GetMapping("/month/{number}")
    public ResponseEntity<List<UtilityMeterDto>> getAllByMonth(
            @PathVariable Integer number,
            @AuthenticationPrincipal User user
    ) {
        List<UtilityMeterDto> utilityMetersDto = getUtilityMeterByMonth.execute(number, user.getId());
        return ResponseEntity.ok(utilityMetersDto);
    }

    @PreAuthorize("hasAnyAuthority({'USER', 'ADMIN'})")
    @GetMapping("/last")
    public ResponseEntity<List<UtilityMeterDto>> getLast(@AuthenticationPrincipal User user) {
        List<UtilityMeterDto> utilityMetersDto = getLastUtilityMeter.execute(user.getId());
        return ResponseEntity.ok(utilityMetersDto);
    }

    @PreAuthorize("hasAnyAuthority({'USER', 'ADMIN'})")
    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody Map<String, Double> meters,
            @AuthenticationPrincipal User user
    ) {
        submitUtilityMeter.execute(meters, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
