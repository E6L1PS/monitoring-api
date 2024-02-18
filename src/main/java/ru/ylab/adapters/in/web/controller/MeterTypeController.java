package ru.ylab.adapters.in.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ylab.adapters.in.web.dto.MeterTypeDto;
import ru.ylab.application.in.AddNewMeterType;
import ru.ylab.application.in.GetUtilityMeterTypes;
import ru.ylab.application.mapper.MeterTypeMapper;
import ru.ylab.domain.model.MeterType;

import java.util.List;

/**
 * Создан: 18.02.2024.
 *
 * @author Pesternikov Danil
 */
@RestController
@RequestMapping("type")
@RequiredArgsConstructor
public class MeterTypeController {

    private final GetUtilityMeterTypes getUtilityMeterTypes;

    private final AddNewMeterType addNewMeterType;

    private final MeterTypeMapper meterTypeMapper;

    @GetMapping
    public List<MeterTypeDto> getAll() {
        List<MeterType> meterTypes = getUtilityMeterTypes.execute();
        List<MeterTypeDto> meterTypesDto = meterTypeMapper.toListDto(meterTypes);
        return meterTypesDto;
    }

    @PostMapping
    public void save(@RequestBody MeterType meterType) {
        addNewMeterType.execute(meterType);
        //TODO return status
    }
}
