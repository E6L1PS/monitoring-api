package ru.ylab.application.in;

import ru.ylab.adapters.in.web.dto.UtilityMeterDto;

import java.util.List;
import java.util.Map;

/**
 * Создан: 27.02.2024.
 *
 * @author Pesternikov Danil
 */
public interface MeterService {

    List<UtilityMeterDto> getAll();

    List<UtilityMeterDto> getAllById(Long userId);

    List<UtilityMeterDto> getLastById(Long userId);

    List<UtilityMeterDto> getByMonth(Integer monthNumber, Long userId);

    void save(Map<String, Double> mapMeters, Long userId);

}
