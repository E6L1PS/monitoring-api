package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.adapters.out.persistence.rowmapper.UserRowMapper;
import ru.ylab.application.exception.UserNotFoundException;
import ru.ylab.application.out.UserRepository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Objects;

/**
 * Класс {@code UserRepositoryImpl} представляет собой реализацию интерфейса {@link UserRepository},
 * предоставляя методы для взаимодействия с данными о пользователях в системе мониторинга.
 *
 * <p>Этот класс помечен аннотацией {@link Repository} для обеспечения использования единственного
 * экземпляра на протяжении всего приложения. Также имеет конструктор без аргументов, помеченный
 * аннотацией {@link NoArgsConstructor}.
 *
 * <p>Реализация включает SQL-запросы для извлечения и сохранения информации о пользователях в базе данных.
 *
 * @author Pesternikov Danil
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    /**
     * SQL-запрос для вставки нового пользователя в базу данных.
     */
    private static final String SQL_INSERT = """
            INSERT INTO monitoring_schema.user
            (username, password, role)
            VALUES (?, ?, ?)
            """;

    /**
     * SQL-запрос для выбора пользователя по имени пользователя из базы данных.
     */
    private static final String SQL_SELECT_USER_BY_USERNAME = """
            SELECT id, username, password, role
            FROM monitoring_schema.user
            WHERE username = ?;
            """;

    /**
     * SQL-запрос для подсчета пользователей по имени пользователя.
     */
    private static final String SQL_SELECT_COUNT_BY_USERNAME = """
            SELECT COUNT(*)
            FROM monitoring_schema.user
            WHERE username = ?;
            """;

    private final JdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity getByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject(
                    SQL_SELECT_USER_BY_USERNAME,
                    new Object[]{username},
                    new UserRowMapper());

        } catch (Exception e) {
            throw new UserNotFoundException("User Not Found");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isAlreadyExists(String username) {
        return jdbcTemplate.queryForObject(SQL_SELECT_COUNT_BY_USERNAME, new Object[]{username}, Integer.class) > 0;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long save(UserEntity userEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
            ps.setString(1, userEntity.getUsername());
            ps.setString(2, userEntity.getPassword());
            ps.setObject(3, userEntity.getRole(), Types.OTHER);
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
