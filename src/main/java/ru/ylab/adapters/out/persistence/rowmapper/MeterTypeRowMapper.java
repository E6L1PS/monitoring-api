package ru.ylab.adapters.out.persistence.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.ylab.adapters.out.persistence.entity.MeterTypeEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Создан: 20.02.2024.
 *
 * @author Pesternikov Danil
 */
public class MeterTypeRowMapper implements RowMapper<MeterTypeEntity> {

    @Override
    public MeterTypeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return MeterTypeEntity.builder()
                .name(rs.getString("name"))
                .build();
    }
}
