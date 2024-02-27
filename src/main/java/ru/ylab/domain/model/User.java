package ru.ylab.domain.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Класс, представляющий сущность пользователя.
 *
 * <p>Данный класс использует аннотации Lombok для автоматической генерации
 * кода, также содержит методы для проверки валидности имени пользователя
 * и пароля.</p>
 *
 * @author Pesternikov Danil
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User implements UserDetails {

    /**
     * Уникальный идентификатор пользователя.
     */
    private Long id;

    /**
     * имя пользователя.
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

    /**
     * Проверяет валидность имени пользователя.
     *
     * @return true, если имя пользователя валидно, иначе false.
     */
    public boolean usernameIsValid() {
        return username != null && username.matches("\\w{5,15}");
    }

    /**
     * Проверяет валидность пароля.
     *
     * @return true, если пароль валиден, иначе false.
     */
    public boolean passwordIsValid() {
        return password != null && password.matches("\\w{5,15}");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//        //return Collections.singleton(role);
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
}