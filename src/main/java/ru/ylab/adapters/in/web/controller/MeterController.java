package ru.ylab.adapters.in.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.application.in.*;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.User;
import ru.ylab.domain.model.UtilityMeter;

import java.util.List;
import java.util.Map;

/**
 * Создан: 18.02.2024.
 *
 * @author Pesternikov Danil
 */
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

    private final UtilityMeterMapper utilityMeterMapper;

    @PreAuthorize("hasRole(Role.ADMIN.getName())")
    @GetMapping("/all")
    public List<UtilityMeterDto> getAll() {
        List<UtilityMeter> utilityMeters = getAllUtilityMeter.execute();
        List<UtilityMeterDto> utilityMetersDto = utilityMeterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }

    @PreAuthorize("hasRole(Role.USER.getName())")
    @GetMapping
    public List<UtilityMeterDto> getAllById(@AuthenticationPrincipal User user) {
        List<UtilityMeter> utilityMetersById = getAllUtilityMeterById.execute(user.getId());
        List<UtilityMeterDto> utilityMetersDto = utilityMeterMapper.toListDto(utilityMetersById);
        return utilityMetersDto;
    }

    @GetMapping("/month/{number}")
    public List<UtilityMeterDto> getAllByMonth(@PathVariable Integer number,
                                               @AuthenticationPrincipal User user
    ) {
        List<UtilityMeter> utilityMeters = getUtilityMeterByMonth.execute(number, user.getId());
        List<UtilityMeterDto> utilityMetersDto = utilityMeterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }

    @GetMapping("/last")
    public List<UtilityMeterDto> getLast(@AuthenticationPrincipal User user) {
        List<UtilityMeter> utilityMeters = getLastUtilityMeter.execute(user.getId());
        List<UtilityMeterDto> utilityMetersDto = utilityMeterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }

    @PostMapping
    public List<UtilityMeterDto> save(@RequestBody Map<String, Double> meters,
                                      @AuthenticationPrincipal User user
    ) {
        List<UtilityMeter> utilityMeters = submitUtilityMeter.execute(meters, user.getId());
        //TODO validation
        List<UtilityMeterDto> utilityMetersDto = utilityMeterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }
}
