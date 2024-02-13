package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.adapters.util.ConnectionManager;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.out.AuditRepository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс {@code AuditRepositoryImpl} представляет собой реализацию интерфейса {@link AuditRepository},
 * предоставляя методы для взаимодействия с данными аудита в системе мониторинга.
 *
 * <p>Этот класс помечен аннотацией {@link Singleton} для обеспечения использования единственного
 * экземпляра на протяжении всего приложения. Также имеет конструктор без аргументов, помеченный
 * аннотацией {@link NoArgsConstructor}.
 *
 * <p>Реализация включает SQL-запросы для извлечения и сохранения информации об аудите в базе данных.
 *
 * @author Pesternikov Danil
 */
@Singleton
@NoArgsConstructor
public class AuditRepositoryImpl implements AuditRepository {

    /**
     * SQL-запрос для выбора всех записей аудита из базы данных, упорядоченных по времени создания.
     */
    private static final String SQL_SELECT_ALL = """
            SELECT * FROM monitoring_schema.audit
            ORDER BY created_at
            """;


    /**
     * SQL-запрос для вставки новой записи аудита в базу данных.
     */
    private static final String SQL_INSERT = """
            INSERT INTO monitoring_schema.audit
            (info, created_at, user_id) 
            VALUES (?, ?, ?)
            """;

    @Autowired
    private ConnectionManager connectionManager;

    public AuditRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuditEntity> findAll() {
        try (var connection = connectionManager.get();
             var statement = connection.prepareStatement(SQL_SELECT_ALL)) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(AuditEntity auditEntity) {
        try (var connection = connectionManager.get();
             var statement = connection.prepareStatement(SQL_INSERT)) {
            statement.setString(1, auditEntity.getInfo());
            statement.setTimestamp(2, Timestamp.valueOf(auditEntity.getDateTime()));
            statement.setLong(3, auditEntity.getUserId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
