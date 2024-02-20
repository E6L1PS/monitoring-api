package ru.ylab.adapters.out.persistence.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.ylab.adapters.out.persistence.entity.UtilityMeterEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Создан: 20.02.2024.
 *
 * @author Pesternikov Danil
 */
public class UtilityMeteRowMapper implements RowMapper<UtilityMeterEntity> {

    @Override
    public UtilityMeterEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UtilityMeterEntity.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .type(rs.getString("type"))
                .counter(rs.getDouble("counter"))
                .readingsDate(rs.getDate("readings_date").toLocalDate())
                .build();
    }
}
