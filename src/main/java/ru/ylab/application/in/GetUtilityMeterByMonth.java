package ru.ylab.application.in;

import ru.ylab.application.model.UtilityMeterModel;

import java.util.List;

public interface GetUtilityMeterByMonth {

    List<UtilityMeterModel> execute(Integer month);
}
