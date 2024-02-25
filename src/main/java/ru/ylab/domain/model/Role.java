package ru.ylab.domain.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Перечисление, представляющее роли в системе.
 * Роли могут быть ADMIN (администратор) или USER (пользователь).
 *
 * @author Pesternikov Danil
 */
public enum Role implements GrantedAuthority {

    /**
     * Роль администратора. Пользователь с этой ролью обладает
     * полными правами доступа и контроля в системе.
     */
    ADMIN,

    /**
     * Роль пользователя. Пользователь с этой ролью имеет ограниченные
     * права по сравнению с администратором.
     */
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
