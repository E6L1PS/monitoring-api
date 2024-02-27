package ru.ylab.adapters.out.persistence.entity;

import lombok.*;
import ru.ylab.domain.model.Role;

/**
 * Представляет сущность пользователя для бд.
 *
 * @author Pesternikov Danil
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserEntity {

    /**
     * Уникальный идентификатор пользователя.
     */
    private Long id;

    /**
     * Имя пользователя.
     */
    private String username;

    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Роль пользователя.
     */
    private Role role;
}
