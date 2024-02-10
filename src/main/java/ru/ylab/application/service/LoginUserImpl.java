package ru.ylab.application.service;

import ru.ylab.adapters.out.persistence.entity.AuditEntity;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.exception.IncorrectPasswordException;
import ru.ylab.application.exception.UserNotFoundException;
import ru.ylab.application.in.LoginUser;
import ru.ylab.application.model.LoginModel;
import ru.ylab.application.out.AuditRepository;
import ru.ylab.application.out.UserRepository;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Singleton
public class LoginUserImpl implements LoginUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    /**
     * {@inheritDoc}
     *
     * @throws UserNotFoundException      в случае если пользователь с таким username не найден
     * @throws IncorrectPasswordException в случае если пользователь ввел не верный пароль
     */
    @Override
    public void execute(LoginModel loginModel) {
        UserEntity userEntity = userRepository.getByUsername(loginModel.username());

        if (userEntity == null) {
            throw new UserNotFoundException("Пользователь с таким username не найден!");
        }

        if (Objects.equals(userEntity.getPassword(), loginModel.password())) {
            userRepository.setupCurrentUser(userEntity);
            auditRepository.save(
                    AuditEntity.builder()
                            .info("Авторизация выполнена")
                            .dateTime(LocalDateTime.now())
                            .userId(userEntity.getId())
                            .build()
            );
        } else {
            throw new IncorrectPasswordException("Пароль неверный!");
        }
    }
}
