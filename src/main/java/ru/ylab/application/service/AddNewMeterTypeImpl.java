package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.in.web.dto.MeterTypeDto;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.application.exception.MeterTypeAlreadyExistsException;
import ru.ylab.application.in.AddNewMeterType;
import ru.ylab.application.mapper.MeterTypeMapper;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.MeterType;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Loggable
@Transactional
@Service
@RequiredArgsConstructor
public class AddNewMeterTypeImpl implements AddNewMeterType {

    private final MeterTypeRepository meterTypeRepository;

    private final MeterTypeMapper meterTypeMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(MeterTypeDto meterTypeDto) {
        if (meterTypeRepository.isMeterTypeExists(meterTypeDto.name())) {
            throw new MeterTypeAlreadyExistsException("MeterType " + meterTypeDto.name() + " already exists.");
        }

        MeterType meterType = meterTypeMapper.toDomain(meterTypeDto);
        //internal business logic with domain model if needed
        MeterTypeEntity meterTypeEntity = meterTypeMapper.toEntity(meterType);
        meterTypeRepository.save(meterTypeEntity);
    }
}
