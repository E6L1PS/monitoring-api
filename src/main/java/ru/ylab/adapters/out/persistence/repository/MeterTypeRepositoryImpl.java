package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;
import ru.ylab.adapters.out.persistence.util.ConnectionManager;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.out.MeterTypeRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
@NoArgsConstructor
public class MeterTypeRepositoryImpl implements MeterTypeRepository {

    private static final String SQL_SELECT_ALL = """
            SELECT * FROM monitoring_schema.meter_type
            """;

    private static final String SQL_SELECT_COUNT_BY_NAME = """
            SELECT COUNT(*) FROM monitoring_schema.meter_type
            WHERE name = ?;
            """;

    private static final String SQL_INSERT = """
            INSERT INTO monitoring_schema.meter_type 
            (name) 
            VALUES (?)
            """;

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
