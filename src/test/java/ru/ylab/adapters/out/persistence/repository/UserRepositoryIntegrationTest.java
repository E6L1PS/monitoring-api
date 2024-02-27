package ru.ylab.adapters.out.persistence.repository;

import org.junit.jupiter.api.*;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.Role;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Создан: 12.02.2024.
 *
 * @author Pesternikov Danil
 */
@DisplayName("Интеграционный тест для UserRepository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryIntegrationTest extends BaseIntegrationTest {

    private final UserRepository userRepository = new UserRepositoryImpl(jdbcTemplate);

    @Test
    @Order(1)
    @DisplayName("Получение пользователей по имени после миграции - ожидается 2 пользователя")
    void getByUsernameAfterMigration() {
        var expectedWithAdminRole = UserEntity.builder()
                .id(1L)
                .username("admin")
                .password("$2a$12$tCwfa/Csf41FaHejWjkiiOjzIoZsZmL7EPuTIllM4ME5FeAaCXlta")
                .role(Role.ADMIN)
                .build();

        var expectedWithUserRole = UserEntity.builder()
                .id(2L)
                .username("user")
                .password("$2a$12$ZFI1Zx4Cm00zELIY1uaDRu1y/2ZCFKYPc4gXtIJCeyNcSvo7p7uSe")
                .role(Role.USER)
                .build();

        var actualAdmin = userRepository.getByUsername("admin");
        var actualUser = userRepository.getByUsername("user");

        assertThat(actualAdmin)
                .usingRecursiveComparison()
                .isEqualTo(expectedWithAdminRole);
        assertThat(actualUser)
                .usingRecursiveComparison()
                .isEqualTo(expectedWithUserRole);
    }

    @Test
    @Order(2)
    @DisplayName("Проверка сохранения нового пользователя")
    void save() {
        var expectedNewUser = UserEntity.builder()
                .id(3L)
                .username("newuser")
                .password("newuser")
                .role(Role.USER)
                .build();

        userRepository.save(expectedNewUser);

        var actualNewUser = userRepository.getByUsername("newuser");

        assertThat(actualNewUser)
                .usingRecursiveComparison()
                .isEqualTo(expectedNewUser);
    }

    @Test
    @Order(3)
    @DisplayName("Пользователь с таким username уже существует")
    void alreadyExists() {
        var isAlreadyExists = userRepository.isAlreadyExists("newuser");
        assertThat(isAlreadyExists).isTrue();

    }

    @Test
    @Order(4)
    @DisplayName("Поьзователя с таким username не существует")
    void notAlreadyExists() {
        var isAlreadyExists = userRepository.isAlreadyExists("newuser1");
        assertThat(isAlreadyExists).isFalse();
    }
}
