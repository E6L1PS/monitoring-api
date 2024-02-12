package ru.ylab.application.service;

import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.exception.NotValidUsernameOrPasswordException;
import ru.ylab.application.exception.UsernameAlreadyExistsException;
import ru.ylab.application.in.RegisterUser;
import ru.ylab.application.mapper.UserMapper;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.User;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
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
    public void execute(User user) {
        if (!userRepository.isAlreadyExists(user.getUsername())) {
            if (user.usernameIsValid() && user.passwordIsValid()) {
                userRepository.save(UserMapper.INSTANCE.userToUserEntity(user));
            } else {
                throw new NotValidUsernameOrPasswordException("Not Valid Username Or Password!");
            }
        } else {
            throw new UsernameAlreadyExistsException(
                    "Пользователь с именем: '" + user.getUsername() + "' уже существует!");
        }
    }
}
