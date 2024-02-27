package ru.ylab.application.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.adapters.in.web.dto.UtilityMeterDto;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.application.exception.MonthlySubmitLimitException;
import ru.ylab.application.exception.NotValidMeterTypeException;
import ru.ylab.application.mapper.MeterMapper;
import ru.ylab.application.out.MeterRepository;
import ru.ylab.application.out.MeterTypeRepository;
import ru.ylab.domain.model.UtilityMeter;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Создан: 28.02.2024.
 *
 * @author Pesternikov Danil
 */
@ExtendWith(MockitoExtension.class)
class MeterServiceImplTest {

    private static List<UtilityMeterDto> utilityMetersDto;

    private static List<UtilityMeter> utilityMeters;

    private static List<UtilityMeterEntity> utilityMeterEntities;

    private static UtilityMeterEntity utilityMeterEntity;

    private static Map<String, Double> validMeters;

    private static Map<String, Double> notValidMeters;

    private static List<MeterTypeEntity> meterTypes;

    private static Long userId;

    @Mock
    private MeterRepository meterRepository;

    @Mock
    private MeterTypeRepository meterTypeRepository;

    @Mock
    private MeterMapper meterMapper;

    @InjectMocks
    private MeterServiceImpl meterService;

    @BeforeAll
    static void setUp() {
        utilityMeterEntities = List.of(UtilityMeterEntity.builder().build());
        MeterMapper mapper = Mappers.getMapper(MeterMapper.class);
        utilityMeters = mapper.toListDomain(utilityMeterEntities);
        utilityMetersDto = mapper.toListDto(utilityMeters);
        utilityMeterEntity = UtilityMeterEntity.builder().build();
        utilityMeters = mapper.toListDomain(List.of(utilityMeterEntity));
        utilityMetersDto = mapper.toListDto(utilityMeters);
        validMeters = Map.of("Холодная вода", 100.0);
        notValidMeters = Map.of("asdadsadasdа", 100.0);
        userId = 1L;
        meterTypes = List.of(MeterTypeEntity.builder().name("Холодная вода").build());
    }

    @Test
    void getAll() {
        when(meterRepository.findAll()).thenReturn(utilityMeterEntities);
        when(meterMapper.toListDomain(anyList())).thenReturn(utilityMeters);
        when(meterMapper.toListDto(anyList())).thenReturn(utilityMetersDto);

        List<UtilityMeterDto> utilityMetersDto = meterService.getAll();

        assertThat(utilityMetersDto).hasSize(1);
        assertThat(utilityMetersDto).isNotNull();
        verify(meterRepository).findAll();
    }

    @Test
    void getAllById() {
        when(meterRepository.findAllByUserId(anyLong())).thenReturn(utilityMeterEntities);
        when(meterMapper.toListDomain(anyList())).thenReturn(utilityMeters);
        when(meterMapper.toListDto(anyList())).thenReturn(utilityMetersDto);

        List<UtilityMeterDto> utilityMetersDto = meterService.getAllById(1L);

        assertThat(utilityMetersDto).isNotNull();
        verify(meterRepository).findAllByUserId(anyLong());
    }

    @Test
    void getLastById() {
        when(meterRepository.findLastByUserId(anyLong())).thenReturn(utilityMeterEntities);
        when(meterMapper.toListDomain(anyList())).thenReturn(utilityMeters);
        when(meterMapper.toListDto(anyList())).thenReturn(utilityMetersDto);

        List<UtilityMeterDto> utilityMetersDto = meterService.getLastById(1L);

        assertThat(utilityMetersDto).hasSize(1);
        assertThat(utilityMetersDto).isNotNull();
        verify(meterRepository).findLastByUserId(anyLong());
    }

    @Test
    void getByMonth() {
        when(meterRepository.findByMonthAndUserId(anyInt(), anyLong())).thenReturn(utilityMeterEntities);
        when(meterMapper.toListDomain(anyList())).thenReturn(utilityMeters);
        when(meterMapper.toListDto(anyList())).thenReturn(utilityMetersDto);

        List<UtilityMeterDto> utilityMetersDto = meterService.getByMonth(1, 1L);

        assertThat(utilityMetersDto).hasSize(1);
        assertThat(utilityMetersDto).isNotNull();
        verify(meterRepository).findByMonthAndUserId(anyInt(), anyLong());
    }

    @Test
    void save() {
        when(meterRepository.isSubmitted(any())).thenReturn(false);
        when(meterTypeRepository.findAll()).thenReturn(meterTypes);
        doNothing().when(meterRepository).saveAll(anyList());

        meterService.save(validMeters, userId);

        verify(meterRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("Если в этом месяце уже подавали показания")
    void save_ThrowsMonthlySubmitLimitException() {
        when(meterRepository.isSubmitted(any())).thenReturn(true);

        assertThatThrownBy(() -> meterService.save(validMeters, userId))
                .isInstanceOf(MonthlySubmitLimitException.class);
    }

    @Test
    @DisplayName("Если поданы не валидные данные")
    void save_ThrowsNotValidMeterTypeException() {
        when(meterRepository.isSubmitted(any())).thenReturn(false);
        when(meterTypeRepository.findAll()).thenReturn(meterTypes);

        assertThatThrownBy(() -> meterService.save(notValidMeters, userId))
                .isInstanceOf(NotValidMeterTypeException.class);
    }

}