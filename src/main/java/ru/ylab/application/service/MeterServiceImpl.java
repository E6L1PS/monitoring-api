package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.exception.MonthlySubmitLimitException;
import ru.ylab.application.exception.NotValidMeterTypeException;
import ru.ylab.application.in.MeterService;
import ru.ylab.application.mapper.MeterMapper;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.UtilityMeter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Создан: 27.02.2024.
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@Transactional
@Service
@RequiredArgsConstructor
public class MeterServiceImpl implements MeterService {

    private final MeterRepository meterRepository;

    private final MeterTypeRepository meterTypeRepository;

    private final MeterMapper meterMapper;

    @Override
    public List<UtilityMeterDto> getAll() {
        List<UtilityMeterEntity> utilityMeterEntities = meterRepository.findAll();
        List<UtilityMeter> utilityMeters = meterMapper.toListDomain(utilityMeterEntities);
        //internal business logic with domain model if needed
        List<UtilityMeterDto> utilityMetersDto = meterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }

    @Override
    public List<UtilityMeterDto> getAllById(Long userId) {
        List<UtilityMeterEntity> utilityMeterEntities = meterRepository.findAllByUserId(userId);
        List<UtilityMeter> utilityMeters = meterMapper.toListDomain(utilityMeterEntities);
        //internal business logic with domain model if needed
        List<UtilityMeterDto> utilityMetersDto = meterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }

    @Override
    public List<UtilityMeterDto> getLastById(Long userId) {
        List<UtilityMeterEntity> utilityMeterEntities = meterRepository.findLastByUserId(userId);
        List<UtilityMeter> utilityMeters = meterMapper.toListDomain(utilityMeterEntities);
        //internal business logic with domain model if needed
        List<UtilityMeterDto> utilityMetersDto = meterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }

    @Override
    public List<UtilityMeterDto> getByMonth(Integer monthNumber, Long userId) {
        List<UtilityMeterEntity> utilityMeterEntities = meterRepository.findByMonthAndUserId(monthNumber, userId);
        List<UtilityMeter> utilityMeters = meterMapper.toListDomain(utilityMeterEntities);
        //internal business logic with domain model if needed
        List<UtilityMeterDto> utilityMetersDto = meterMapper.toListDto(utilityMeters);
        return utilityMetersDto;
    }

    @Override
    public void save(Map<String, Double> mapMeters, Long userId) {
        if (meterRepository.isSubmitted(userId)) {
            throw new MonthlySubmitLimitException("В этом месяце вы уже подали!");
        }

        if (!isValid(mapMeters.keySet())) {
            throw new NotValidMeterTypeException("Такой тип не существует!");
        }

        List<UtilityMeterEntity> utilityMeterEntities = mapMeters.entrySet().stream()
                .map(entry -> UtilityMeterEntity.builder()
                        .userId(userId)
                        .type(entry.getKey())
                        .counter(entry.getValue())
                        .readingsDate(LocalDate.now())
                        .build())
                .toList();

        meterRepository.saveAll(utilityMeterEntities);
    }

    private Boolean isValid(Set<String> inputMeters) {
        List<String> meterTypes = meterTypeRepository.findAll().stream()
                .map(MeterTypeEntity::getName)
                .toList();
        return inputMeters.containsAll(meterTypes);
    }

}
