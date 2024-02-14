package ru.ylab.application.service;

import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.exception.NotValidUsernameOrPasswordException;
import ru.ylab.application.exception.UsernameAlreadyExistsException;
import ru.ylab.application.in.RegisterUser;
import ru.ylab.application.mapper.UserMapper;
import ru.ylab.application.out.UserRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;
import ru.ylab.domain.model.User;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@Singleton
public class RegisterUserImpl implements RegisterUser {

    @Autowired
    private UserRepository userRepository;

    /**
     * {@inheritDoc}
     *
     * @throws NotValidUsernameOrPasswordException в случае некорректного username/password
     * @throws UsernameAlreadyExistsException      в случае если пользователь уже существует с таким username
     */
    @Override
    public Long execute(User user) {
        if (!userRepository.isAlreadyExists(user.getUsername())) {
            if (user.usernameIsValid() && user.passwordIsValid()) {
                UserEntity userEntity = UserMapper.INSTANCE.toEntity(user);
                return userRepository.save(userEntity);
            } else {
                throw new NotValidUsernameOrPasswordException("Not Valid Username Or Password!");
            }
        } else {
            throw new UsernameAlreadyExistsException(
                    "Пользователь с именем: '" + user.getUsername() + "' уже существует!");
        }
    }
}
