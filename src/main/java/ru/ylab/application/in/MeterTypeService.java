package ru.ylab.application.in;

import ru.ylab.adapters.in.web.dto.MeterTypeDto;

import java.util.List;

/**
 * Создан: 27.02.2024.
 *
 * @author Pesternikov Danil
 */
public interface MeterTypeService {

    List<MeterTypeDto> getAll();

    void save(MeterTypeDto meterTypeDto);

}
