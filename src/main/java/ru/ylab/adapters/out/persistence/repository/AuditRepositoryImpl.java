package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.adapters.out.persistence.rowmapper.AuditRowMapper;
import ru.ylab.application.out.AuditRepository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Класс {@code AuditRepositoryImpl} представляет собой реализацию интерфейса {@link AuditRepository},
 * предоставляя методы для взаимодействия с данными аудита в системе мониторинга.
 *
 * <p>Этот класс помечен аннотацией {@link Repository} для обеспечения использования единственного
 * экземпляра на протяжении всего приложения. Также имеет конструктор без аргументов, помеченный
 * аннотацией {@link NoArgsConstructor}.
 *
 * <p>Реализация включает SQL-запросы для извлечения и сохранения информации об аудите в базе данных.
 *
 * @author Pesternikov Danil
 */
@Repository
@RequiredArgsConstructor
public class AuditRepositoryImpl implements AuditRepository {

    /**
     * SQL-запрос для выбора всех записей аудита из базы данных, упорядоченных по времени создания.
     */
    private static final String SQL_SELECT_ALL = """
            SELECT id, info, created_at, user_id
            FROM monitoring_schema.audit
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

    private final JdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuditEntity> findAll() {
        return jdbcTemplate.query(
                SQL_SELECT_ALL,
                new AuditRowMapper()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(AuditEntity auditEntity) {
        jdbcTemplate.update(
                SQL_INSERT,
                auditEntity.getInfo(),
                Timestamp.valueOf(auditEntity.getDateTime()),
                auditEntity.getUserId()
        );
    }
}
