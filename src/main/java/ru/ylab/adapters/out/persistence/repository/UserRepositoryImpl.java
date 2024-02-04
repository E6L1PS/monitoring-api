package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.adapters.out.persistence.util.ConnectionManager;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.Role;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

@Singleton
@NoArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_INSERT = """
            INSERT INTO monitoring_schema.user
            (username, password, role)
            VALUES (?, ?, ?)
            """;

    private static final String SQL_SELECT_USER_BY_USERNAME = """
            SELECT * FROM monitoring_schema.user
            WHERE username = ?;
            """;

    private static final String SQL_SELECT_COUNT_BY_USERNAME = """
            SELECT COUNT(*) FROM monitoring_schema.user
            WHERE username = ?;
            """;

    private UserEntity currentUser;

    @Override
    public UserEntity getByUsername(String username) {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_SELECT_USER_BY_USERNAME)) {
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
                throw new RuntimeException("User Not Found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean isAlreadyExists(String username) {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_SELECT_COUNT_BY_USERNAME)) {
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

    @Override
    public Long save(UserEntity userEntity) {
        try (var statement = ConnectionManager.open().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
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

    @Override
    public Long getCurrentUserId() {
        return currentUser.getId();
    }

    @Override
    public Role getCurrentRoleUser() {
        if (currentUser == null) {
            return Role.USER;
        } else {
            return currentUser.getRole();
        }
    }

    @Override
    public UserEntity setupCurrentUser(UserEntity userEntity) {
        this.currentUser = userEntity;
        return userEntity;
    }

    @Override
    public void logout() {
        currentUser = null;
    }
}
