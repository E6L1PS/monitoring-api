package ru.ylab.application.service;

import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.exception.NotValidUsernameOrPasswordException;
import ru.ylab.application.exception.UsernameAlreadyExistsException;
import ru.ylab.application.in.RegisterUser;
import ru.ylab.application.mapper.UserMapper;
import ru.ylab.application.model.RegisterModel;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.User;

@Singleton
public class RegisterUserImpl implements RegisterUser {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void execute(RegisterModel registerModel) {
        if (!userRepository.isAlreadyExists(registerModel.username())) {
            User user = UserMapper.INSTANCE.toUser(registerModel);
            if (user.usernameIsValid() && user.passwordIsValid()) {
                userRepository.save(UserMapper.INSTANCE.userToUserEntity(user));
            } else {
                throw new NotValidUsernameOrPasswordException("Not Valid Username Or Password!");
            }
        } else {
            throw new UsernameAlreadyExistsException("Пользователь с именем: '" +
                    registerModel.username() + "' уже существует!");
        }
    }
}
