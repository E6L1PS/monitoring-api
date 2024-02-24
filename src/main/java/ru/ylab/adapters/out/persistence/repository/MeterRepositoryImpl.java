package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.adapters.out.persistence.rowmapper.UtilityMeteRowMapper;
import ru.ylab.application.out.MeterRepository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Класс {@code MeterRepositoryImpl} представляет собой реализацию интерфейса {@link MeterRepository},
 * предоставляя методы для взаимодействия с данными счетчиков в системе мониторинга.
 *
 * <p>Этот класс помечен аннотацией {@link Repository} для обеспечения использования единственного
 * экземпляра на протяжении всего приложения. Также имеет конструктор без аргументов, помеченный
 * аннотацией {@link NoArgsConstructor}.
 *
 * <p>Реализация включает SQL-запросы для извлечения и сохранения информации о счетчиках в базе данных.
 *
 * @author Pesternikov Danil
 */
@Repository
@RequiredArgsConstructor
public class MeterRepositoryImpl implements MeterRepository {

    /**
     * SQL-запрос для выбора всех записей о счетчиках из базы данных.
     */
    private static final String SQL_SELECT_ALL = """
            SELECT id, counter, readings_date, type, user_id
            FROM monitoring_schema.utility_meter
            """;

    /**
     * SQL-запрос для выбора всех записей о счетчиках по идентификатору пользователя из базы данных.
     */
    private static final String SQL_SELECT_ALL_BY_USER_ID = """
            SELECT id, counter, readings_date, type, user_id
            FROM monitoring_schema.utility_meter
            WHERE user_id = ?
            """;

    /**
     * SQL-запрос для выбора последних записей о счетчиках по идентификатору пользователя из базы данных.
     */
    private static final String SQL_SELECT_ALL_BY_USER_ID_LAST = """
            SELECT id, counter, readings_date, type, user_id
            FROM monitoring_schema.utility_meter
            WHERE user_id = ? AND readings_date = (
                SELECT readings_date
                FROM monitoring_schema.utility_meter
                ORDER BY readings_date DESC
                LIMIT 1
                );
            """;

    /**
     * SQL-запрос для подсчета записей о счетчиках по идентификатору пользователя и месяцу.
     */
    private static final String SQL_SELECT_COUNT_BY_USER_ID_AND_DATE = """
            SELECT COUNT(*)
            FROM monitoring_schema.utility_meter
            WHERE user_id = ? AND EXTRACT(MONTH FROM readings_date) = EXTRACT(MONTH FROM CURRENT_DATE)
            """;

    /**
     * SQL-запрос для выбора всех записей о счетчиках по идентификатору пользователя и месяцу.
     */
    private static final String SQL_SELECT_ALL_BY_USER_ID_AND_DATE = """
            SELECT id, counter, readings_date, type, user_id
            FROM monitoring_schema.utility_meter
            WHERE user_id = ? AND EXTRACT(MONTH FROM readings_date) = ?
            """;

    /**
     * SQL-запрос для вставки новой записи о счетчике в базу данных.
     */
    private static final String SQL_INSERT = """
            INSERT INTO monitoring_schema.utility_meter
            (counter, readings_date, user_id, type)
            VALUES (?, ?, ?, ?)
            """;

    private final JdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterEntity> findAll() {
        return jdbcTemplate.query(
                SQL_SELECT_ALL,
                new UtilityMeteRowMapper()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterEntity> findAllByUserId(Long userId) {
        return jdbcTemplate.query(
                SQL_SELECT_ALL_BY_USER_ID,
                new Object[]{userId},
                new UtilityMeteRowMapper()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterEntity> findLastByUserId(Long userId) {
        return jdbcTemplate.query(
                SQL_SELECT_ALL_BY_USER_ID_LAST,
                new Object[]{userId},
                new UtilityMeteRowMapper()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterEntity> findByMonthAndUserId(Integer month, Long userId) {
        return jdbcTemplate.query(
                SQL_SELECT_ALL_BY_USER_ID_AND_DATE,
                new Object[]{userId, month},
                new UtilityMeteRowMapper()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isSubmitted(Long userId) {
        return jdbcTemplate.queryForObject(SQL_SELECT_COUNT_BY_USER_ID_AND_DATE,
                new Object[]{userId},
                Long.class) > 0;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UtilityMeterEntity save(UtilityMeterEntity utilityMeterEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
            ps.setDouble(1, utilityMeterEntity.getCounter());
            ps.setDate(2, Date.valueOf(utilityMeterEntity.getReadingsDate()));
            ps.setLong(3, utilityMeterEntity.getUserId());
            ps.setString(4, utilityMeterEntity.getType());
            return ps;
        }, keyHolder);

        utilityMeterEntity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return utilityMeterEntity;
    }

    @Override
    public void saveAll(List<UtilityMeterEntity> utilityMeterEntities) {
        jdbcTemplate.batchUpdate(SQL_INSERT, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UtilityMeterEntity utilityMeterEntity = utilityMeterEntities.get(i);
                ps.setDouble(1, utilityMeterEntity.getCounter());
                ps.setDate(2, Date.valueOf(utilityMeterEntity.getReadingsDate()));
                ps.setLong(3, utilityMeterEntity.getUserId());
                ps.setString(4, utilityMeterEntity.getType());
            }

            @Override
            public int getBatchSize() {
                return utilityMeterEntities.size();
            }
        });
    }

}
