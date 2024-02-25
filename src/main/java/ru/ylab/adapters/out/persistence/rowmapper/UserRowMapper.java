package ru.ylab.adapters.out.persistence.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.domain.model.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Создан: 20.02.2024.
 *
 * @author Pesternikov Danil
 */
public class UserRowMapper implements RowMapper<UserEntity> {

    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserEntity.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .role(Role.valueOf(rs.getString("role")))
                .build();
    }
}
