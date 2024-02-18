package ru.ylab.adapters.in.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.application.in.*;
import ru.ylab.application.mapper.UtilityMeterMapper;
import ru.ylab.domain.model.UtilityMeter;

import java.util.List;
import java.util.Map;

/**
 * Создан: 18.02.2024.
 *
 * @author Pesternikov Danil
 */
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

    @GetMapping
    public List<UtilityMeterDto> getAll() {
        List<UtilityMeter> utilityMeters = getAllUtilityMeter.execute();
        //TODO List<UtilityMeter> utilityMetersById = getAllUtilityMeterById.execute(userId);
        List<UtilityMeterDto> utilityMetersDto = utilityMeterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }

    @GetMapping("/month/{number}")
    public List<UtilityMeterDto> getAllByMonth(@PathVariable Integer number) {
        List<UtilityMeter> utilityMeters = getUtilityMeterByMonth.execute(number, 1L); //TODO userId
        List<UtilityMeterDto> utilityMetersDto = utilityMeterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }

    @GetMapping("/last")
    public List<UtilityMeterDto> getLast() {
        List<UtilityMeter> utilityMeters = getLastUtilityMeter.execute(1L); //TODO userId
        List<UtilityMeterDto> utilityMetersDto = utilityMeterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }

    @PostMapping
    public List<UtilityMeterDto> save(@RequestBody Map<String, Double> meters) {
        List<UtilityMeter> utilityMeters = submitUtilityMeter.execute(meters, 1L); //TODO userId
        //TODO validation
        List<UtilityMeterDto> utilityMetersDto = utilityMeterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }
}
