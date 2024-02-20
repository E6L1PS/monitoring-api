package ru.ylab.adapters.out.persistence.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.ylab.adapters.out.persistence.entity.AuditEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Создан: 20.02.2024.
 *
 * @author Pesternikov Danil
 */
public class AuditRowMapper implements RowMapper<AuditEntity> {

    @Override
    public AuditEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AuditEntity.builder()
                .id(rs.getLong("id"))
                .info(rs.getString("info"))
                .userId(rs.getLong("user_id"))
                .dateTime(rs.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
