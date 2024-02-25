package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.adapters.out.persistence.rowmapper.MeterTypeRowMapper;
import ru.ylab.application.out.MeterTypeRepository;

import java.util.List;

/**
 * Класс {@code MeterTypeRepositoryImpl} представляет собой реализацию интерфейса {@link MeterTypeRepository},
 * предоставляя методы для взаимодействия с данными о типах счетчиков в системе мониторинга.
 *
 * <p>Этот класс помечен аннотацией {@link Repository} для обеспечения использования единственного
 * экземпляра на протяжении всего приложения. Также имеет конструктор без аргументов, помеченный
 * аннотацией {@link NoArgsConstructor}.
 *
 * <p>Реализация включает SQL-запросы для извлечения и сохранения информации о типах счетчиков в базе данных.
 *
 * @author Pesternikov Danil
 */
@Repository
@RequiredArgsConstructor
public class MeterTypeRepositoryImpl implements MeterTypeRepository {

    /**
     * SQL-запрос для выбора всех записей о типах счетчиков из базы данных.
     */
    private static final String SQL_SELECT_ALL = """
            SELECT name
            FROM monitoring_schema.meter_type
            """;

    /**
     * SQL-запрос для подсчета типов счетчиков по имени.
     */
    private static final String SQL_SELECT_COUNT_BY_NAME = """
            SELECT COUNT(*)
            FROM monitoring_schema.meter_type
            WHERE name = ?;
            """;

    /**
     * SQL-запрос для вставки нового типа счетчика в базу данных.
     */
    private static final String SQL_INSERT = """
            INSERT INTO monitoring_schema.meter_type 
            (name) 
            VALUES (?)
            """;

    private final JdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MeterTypeEntity> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL,
                new MeterTypeRowMapper());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isMeterTypeExists(String typeName) {
        return jdbcTemplate.queryForObject(SQL_SELECT_COUNT_BY_NAME, new Object[]{typeName}, Long.class) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MeterTypeEntity save(MeterTypeEntity meterTypeEntity) {
        jdbcTemplate.update(SQL_INSERT, meterTypeEntity.getName());
        return meterTypeEntity;
    }
}
