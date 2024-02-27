package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.in.web.dto.MeterTypeDto;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.application.in.GetMeterTypes;
import ru.ylab.application.mapper.MeterTypeMapper;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.domain.model.MeterType;
import ru.ylab.aspect.annotation.Loggable;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@Transactional
@Service
@RequiredArgsConstructor
public class GetMeterTypesImpl implements GetMeterTypes {

    private final MeterTypeRepository meterTypeRepository;

    private final MeterTypeMapper meterTypeMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MeterTypeDto> execute() {
        List<MeterTypeEntity> meterTypeEntities = meterTypeRepository.findAll();
        List<MeterType> meterTypes = meterTypeMapper.toListDomain(meterTypeEntities);
        //internal business logic with domain model if needed
        List<MeterTypeDto> meterTypesDto = meterTypeMapper.toListDto(meterTypes);
        return meterTypesDto;
    }
}
