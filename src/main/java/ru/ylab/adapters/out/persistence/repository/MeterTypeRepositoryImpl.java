package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.adapters.out.persistence.util.ConnectionManager;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.out.MeterTypeRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс {@code MeterTypeRepositoryImpl} представляет собой реализацию интерфейса {@link MeterTypeRepository},
 * предоставляя методы для взаимодействия с данными о типах счетчиков в системе мониторинга.
 *
 * <p>Этот класс помечен аннотацией {@link Singleton} для обеспечения использования единственного
 * экземпляра на протяжении всего приложения. Также имеет конструктор без аргументов, помеченный
 * аннотацией {@link NoArgsConstructor}.
 *
 * <p>Реализация включает SQL-запросы для извлечения и сохранения информации о типах счетчиков в базе данных.
 *
 * @author Pesternikov Danil
 */
@Singleton
@NoArgsConstructor
public class MeterTypeRepositoryImpl implements MeterTypeRepository {

    /**
     * SQL-запрос для выбора всех записей о типах счетчиков из базы данных.
     */
    private static final String SQL_SELECT_ALL = """
            SELECT * FROM monitoring_schema.meter_type
            """;

    /**
     * SQL-запрос для подсчета типов счетчиков по имени.
     */
    private static final String SQL_SELECT_COUNT_BY_NAME = """
            SELECT COUNT(*) FROM monitoring_schema.meter_type
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MeterTypeEntity> findAll() {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_SELECT_ALL)) {
            var resultSet = statement.executeQuery();
            List<MeterTypeEntity> meterTypeEntities = new ArrayList<>();
            while (resultSet.next()) {
                meterTypeEntities.add(
                        MeterTypeEntity.builder()
                                .name(resultSet.getString("name"))
                                .build()
                );
            }
            return meterTypeEntities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isMeterTypeExists(String typeName) {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_SELECT_COUNT_BY_NAME)) {
            statement.setString(1, typeName);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1) > 0;
            } else {
                throw new RuntimeException("isMeterTypeExists error!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MeterTypeEntity save(String typeName) {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_INSERT)) {
            statement.setString(1, typeName);
            statement.executeUpdate();
            return MeterTypeEntity.builder().name(typeName).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
