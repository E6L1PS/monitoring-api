package ru.ylab.application.in;

import ru.ylab.adapters.in.web.dto.UtilityMeterModel;

import java.util.List;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
public interface GetAllUtilityMeterById {

    List<UtilityMeterModel> execute(Long userId);
}