package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.adapters.out.persistence.util.ConnectionManager;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.out.AuditRepository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Singleton
@NoArgsConstructor
public class AuditRepositoryImpl implements AuditRepository {

    private static final String SQL_SELECT_ALL = """
            SELECT * FROM monitoring_schema.audit
            ORDER BY created_at
            """;

    private static final String SQL_INSERT = """
            INSERT INTO monitoring_schema.audit
            (info, created_at, user_id) 
            VALUES (?, ?, ?)
            """;

    @Override
    public List<AuditEntity> findAll() {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_SELECT_ALL)) {
            var resultSet = statement.executeQuery();
            List<AuditEntity> audits = new ArrayList<>();
            while (resultSet.next()) {
                audits.add(
                        AuditEntity.builder()
                                .id(resultSet.getLong("id"))
                                .info(resultSet.getString("info"))
                                .userId(resultSet.getLong("user_id"))
                                .dateTime(resultSet.getTimestamp("created_at").toLocalDateTime())
                                .build()
                );
            }
            return audits;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(AuditEntity auditEntity) {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_INSERT)) {
            statement.setString(1, auditEntity.getInfo());
            statement.setTimestamp(2, Timestamp.valueOf(auditEntity.getDateTime()));
            statement.setLong(3, auditEntity.getUserId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
