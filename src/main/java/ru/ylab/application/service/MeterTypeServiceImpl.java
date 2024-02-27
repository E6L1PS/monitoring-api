package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.in.web.dto.MeterTypeDto;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.application.exception.MeterTypeAlreadyExistsException;
import ru.ylab.application.in.MeterTypeService;
import ru.ylab.application.mapper.MeterTypeMapper;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.MeterType;

import java.util.List;

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
public class MeterTypeServiceImpl implements MeterTypeService {

    private final MeterTypeRepository meterTypeRepository;

    private final MeterTypeMapper meterTypeMapper;

    @Override
    public List<MeterTypeDto> getAll() {
        List<MeterTypeEntity> meterTypeEntities = meterTypeRepository.findAll();
        List<MeterType> meterTypes = meterTypeMapper.toListDomain(meterTypeEntities);
        //internal business logic with domain model if needed
        List<MeterTypeDto> meterTypesDto = meterTypeMapper.toListDto(meterTypes);
        return meterTypesDto;
    }

    @Override
    public void save(MeterTypeDto meterTypeDto) {
        if (meterTypeRepository.isMeterTypeExists(meterTypeDto.name())) {
            throw new MeterTypeAlreadyExistsException("MeterType " + meterTypeDto.name() + " already exists.");
        }

        MeterType meterType = meterTypeMapper.toDomain(meterTypeDto);
        //internal business logic with domain model if needed
        MeterTypeEntity meterTypeEntity = meterTypeMapper.toEntity(meterType);
        meterTypeRepository.save(meterTypeEntity);
    }

}
