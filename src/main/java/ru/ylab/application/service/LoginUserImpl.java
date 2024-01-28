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

@Singleton
public class LoginUserImpl implements LoginUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public void execute(LoginModel loginModel) {
        UserEntity userEntity = userRepository.getByUsername(loginModel.username());
        if (userEntity != null) {
            if (Objects.equals(userEntity.getPassword(), loginModel.password())) {
                userRepository.setupCurrentUser(userEntity);
                auditRepository.saveAudit(
                        AuditEntity.builder()
                                .info("Авторизация выполнена")
                                .dateTime(LocalDateTime.now())
                                .username(userEntity.getUsername())
                                .build()
                );
            } else throw new IncorrectPasswordException("Пароль неверный!");
        } else throw new UserNotFoundException("Пользователь с таким username не найден!");
    }
}
