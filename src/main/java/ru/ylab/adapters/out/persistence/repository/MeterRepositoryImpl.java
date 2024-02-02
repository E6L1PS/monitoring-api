package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;
import ru.ylab.adapters.out.persistence.util.ConnectionManager;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.out.MeterRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
@NoArgsConstructor
public class MeterRepositoryImpl implements MeterRepository {

    private static final String SQL_SELECT_ALL = """
            SELECT * FROM utility_meter
            """;

    private static final String SQL_SELECT_ALL_BY_USER_ID = """
            SELECT * FROM utility_meter WHERE user_id = ?
            """;

    private static final String SQL_SELECT_ALL_BY_USER_ID_LAST = """
            SELECT * FROM utility_meter WHERE user_id = ? AND readings_date = (
                SELECT readings_date
                FROM utility_meter
                ORDER BY readings_date
                LIMIT 1
                );
            """;

    private static final String SQL_SELECT_COUNT_BY_USER_ID_AND_DATE = """
            SELECT COUNT(*) FROM utility_meter
            WHERE user_id = ? AND EXTRACT(MONTH FROM readings_date) = EXTRACT(MONTH FROM CURRENT_DATE)
            """;

    private static final String SQL_SELECT_ALL_BY_USER_ID_AND_DATE = """
            SELECT * FROM utility_meter
            WHERE user_id = ? AND EXTRACT(MONTH FROM readings_date) = ?
            """;

    private static final String SQL_INSERT = """
            INSERT INTO utility_meter
            (counter, readings_date, user_id, type)
            VALUES (?, ?, ?, ?)
            """;

    @Override
    public List<UtilityMeterEntity> findAll() {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_SELECT_ALL)) {
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

    @Override
    public List<UtilityMeterEntity> findAllByUserId(Long userId) {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_SELECT_ALL_BY_USER_ID)) {
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

    @Override
    public List<UtilityMeterEntity> findLastByUserId(Long userId) {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_SELECT_ALL_BY_USER_ID_LAST)) {
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

    @Override
    public List<UtilityMeterEntity> findByMonth(Integer month, Long userId) {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_SELECT_ALL_BY_USER_ID_AND_DATE)) {
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

    @Override
    public Boolean isSubmitted(Integer month, Long userId) {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_SELECT_COUNT_BY_USER_ID_AND_DATE)) {
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

    @Override
    public UtilityMeterEntity create(UtilityMeterEntity utilityMeterEntity) {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_INSERT)) {
            statement.setDouble(1, utilityMeterEntity.getCounter());
            statement.setDate(2, Date.valueOf(utilityMeterEntity.getReadingsDate()));
            statement.setLong(3, utilityMeterEntity.getUserId());
            statement.setString(4, utilityMeterEntity.getType());

            statement.executeUpdate();
            return utilityMeterEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
