package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.adapters.util.ConnectionManager;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.exception.UserNotFoundException;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.Role;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 * Класс {@code UserRepositoryImpl} представляет собой реализацию интерфейса {@link UserRepository},
 * предоставляя методы для взаимодействия с данными о пользователях в системе мониторинга.
 *
 * <p>Этот класс помечен аннотацией {@link Singleton} для обеспечения использования единственного
 * экземпляра на протяжении всего приложения. Также имеет конструктор без аргументов, помеченный
 * аннотацией {@link NoArgsConstructor}.
 *
 * <p>Реализация включает SQL-запросы для извлечения и сохранения информации о пользователях в базе данных.
 *
 * @author Pesternikov Danil
 */
@Singleton
@NoArgsConstructor
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

    @Autowired
    private ConnectionManager connectionManager;

    public UserRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity getByUsername(String username) {
        try (var connection = connectionManager.get();
             var statement = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME)) {
            statement.setString(1, username);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return UserEntity.builder()
                        .id(resultSet.getLong("id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .role(Role.valueOf(resultSet.getString("role")))
                        .build();
            } else {
                throw new UserNotFoundException("User Not Found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isAlreadyExists(String username) {
        try (var connection = connectionManager.get();
             var statement = connection.prepareStatement(SQL_SELECT_COUNT_BY_USERNAME)) {
            statement.setString(1, username);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Если пользователь существует, вернуть true
            } else {
                throw new RuntimeException("isAlreadyExists error");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long save(UserEntity userEntity) {
        try (var connection = connectionManager.get();
             var statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, userEntity.getUsername());
            statement.setString(2, userEntity.getPassword());
            statement.setObject(3, userEntity.getRole(), Types.OTHER);
            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();

            if (keys.next()) {
                return keys.getLong("id");
            } else {
                throw new RuntimeException("save error!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
