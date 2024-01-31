package ru.ylab.adapters.out.persistence.repository;

import lombok.NoArgsConstructor;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.annotations.Init;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.Role;

import java.util.HashMap;
import java.util.Map;

@Singleton
@NoArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final Map<String, UserEntity> users = new HashMap<>();
    private UserEntity userEntity;

    @Init
    public void init() {
        var user = UserEntity.builder().username("admin").password("admin").role(Role.ADMIN).build();
        users.put("admin", user);
    }

    @Override
    public void save(UserEntity userEntity) {
        users.put(userEntity.getUsername(), userEntity);
    }

    @Override
    public UserEntity getByUsername(String username) {
        return users.get(username);
    }

    @Override
    public Boolean isAlreadyExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public String getCurrentUsername() {
        return userEntity.getUsername();
    }

    @Override
    public Role getCurrentRoleUser() {
        if (userEntity == null) {
            return Role.USER;
        } else {
            return userEntity.getRole();
        }
    }

    @Override
    public UserEntity setupCurrentUser(UserEntity userEntity) {
        this.userEntity = userEntity;
        return userEntity;
    }

    @Override
    public void logout() {
        userEntity = null;
    }
}
