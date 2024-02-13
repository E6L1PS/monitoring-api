package ru.ylab.application.service;

import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.exception.IncorrectPasswordException;
import ru.ylab.application.exception.UserNotFoundException;
import ru.ylab.application.in.LoginUser;
import ru.ylab.application.out.UserRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.User;

import java.util.Objects;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@Singleton
public class LoginUserImpl implements LoginUser {

    @Autowired
    private UserRepository userRepository;

    /**
     * {@inheritDoc}
     *
     * @return
     * @throws UserNotFoundException      в случае если пользователь с таким username не найден
     * @throws IncorrectPasswordException в случае если пользователь ввел не верный пароль
     */
    @Override
    public UserEntity execute(User user) {
        UserEntity userEntity = userRepository.getByUsername(user.getUsername());

        if (userEntity == null) {
            throw new UserNotFoundException("Пользователь с таким username не найден!");
        }

        if (Objects.equals(userEntity.getPassword(), user.getPassword())) {
            return userEntity;
        } else {
            throw new IncorrectPasswordException("Пароль неверный!");
        }
    }
}
