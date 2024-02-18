package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.adapters.util.ConnectionManager;
import ru.ylab.application.out.MeterRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
@NoArgsConstructor
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

    @Autowired
    private ConnectionManager connectionManager;

    public MeterRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterEntity> findAll() {
        try (var connection = connectionManager.get();
             var statement = connection.prepareStatement(SQL_SELECT_ALL)) {
            var resultSet = statement.executeQuery();
            List<UtilityMeterEntity> utilityMeterEntities = new ArrayList<>();
            while (resultSet.next()) {
                utilityMeterEntities.add(
                        UtilityMeterEntity.builder()
                                .id(resultSet.getLong("id"))
                                .counter(resultSet.getDouble("counter"))
                                .readingsDate(resultSet.getDate("readings_date").toLocalDate())
                                .type(resultSet.getString("type"))
                                .userId(resultSet.getLong("user_id"))
                                .build()
                );
            }
            return utilityMeterEntities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterEntity> findAllByUserId(Long userId) {
        try (var connection = connectionManager.get();
             var statement = connection.prepareStatement(SQL_SELECT_ALL_BY_USER_ID)) {
            statement.setLong(1, userId);
            var resultSet = statement.executeQuery();
            List<UtilityMeterEntity> utilityMeterEntities = new ArrayList<>();
            while (resultSet.next()) {
                utilityMeterEntities.add(
                        UtilityMeterEntity.builder()
                                .id(resultSet.getLong("id"))
                                .counter(resultSet.getDouble("counter"))
                                .readingsDate(resultSet.getDate("readings_date").toLocalDate())
                                .userId(resultSet.getLong("user_id"))
                                .type(resultSet.getString("type"))
                                .build()
                );
            }

            return utilityMeterEntities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterEntity> findLastByUserId(Long userId) {
        try (var connection = connectionManager.get();
             var statement = connection.prepareStatement(SQL_SELECT_ALL_BY_USER_ID_LAST)) {
            statement.setLong(1, userId);
            var resultSet = statement.executeQuery();
            List<UtilityMeterEntity> utilityMeterEntities = new ArrayList<>();
            while (resultSet.next()) {
                utilityMeterEntities.add(
                        UtilityMeterEntity.builder()
                                .id(resultSet.getLong("id"))
                                .counter(resultSet.getDouble("counter"))
                                .readingsDate(resultSet.getDate("readings_date").toLocalDate())
                                .type(resultSet.getString("type"))
                                .userId(resultSet.getLong("user_id"))
                                .build()
                );
            }
            return utilityMeterEntities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UtilityMeterEntity> findByMonthAndUserId(Integer month, Long userId) {
        try (var connection = connectionManager.get();
             var statement = connection.prepareStatement(SQL_SELECT_ALL_BY_USER_ID_AND_DATE)) {
            statement.setLong(1, userId);
            statement.setLong(2, month);

            var resultSet = statement.executeQuery();

            List<UtilityMeterEntity> utilityMeterEntities = new ArrayList<>();
            while (resultSet.next()) {
                utilityMeterEntities.add(
                        UtilityMeterEntity.builder()
                                .id(resultSet.getLong("id"))
                                .counter(resultSet.getDouble("counter"))
                                .readingsDate(resultSet.getDate("readings_date").toLocalDate())
                                .type(resultSet.getString("type"))
                                .userId(resultSet.getLong("user_id"))
                                .build()
                );
            }
            return utilityMeterEntities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isSubmitted(Long userId) {
        try (var connection = connectionManager.get();
             var statement = connection.prepareStatement(SQL_SELECT_COUNT_BY_USER_ID_AND_DATE)) {
            statement.setLong(1, userId);
            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong(1) > 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UtilityMeterEntity save(UtilityMeterEntity utilityMeterEntity) {
        try (var connection = connectionManager.get();
             var statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, utilityMeterEntity.getCounter());
            statement.setDate(2, Date.valueOf(utilityMeterEntity.getReadingsDate()));
            statement.setLong(3, utilityMeterEntity.getUserId());
            statement.setString(4, utilityMeterEntity.getType());
            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                Long meterId = keys.getLong("id");
                utilityMeterEntity.setId(meterId);
            }

            return utilityMeterEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
