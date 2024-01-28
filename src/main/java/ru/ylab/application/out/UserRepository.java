package ru.ylab.application.out;

import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.domain.model.Role;

public interface UserRepository {

    void save(UserEntity userEntity);

    UserEntity getByUsername(String username);

    Boolean isAlreadyExists(String username);

    String getCurrentUsername();

    Role getCurrentRoleUser();

    UserEntity setupCurrentUser(UserEntity userEntity);
}
