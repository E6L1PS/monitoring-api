package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.exception.NotValidUsernameOrPasswordException;
import ru.ylab.application.exception.UsernameAlreadyExistsException;
import ru.ylab.application.in.RegisterUser;
import ru.ylab.application.mapper.UserMapper;
import ru.ylab.application.out.UserRepository;
import ru.ylab.infrastructure.aspect.annotation.Auditable;
import ru.ylab.infrastructure.aspect.annotation.Loggable;
import ru.ylab.domain.model.User;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@RequiredArgsConstructor
@Transactional
@Service
public class RegisterUserImpl implements RegisterUser {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

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
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                UserEntity userEntity = userMapper.toEntity(user);
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
