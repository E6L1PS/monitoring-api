package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.in.web.dto.RegisterDto;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.exception.NotValidUsernameOrPasswordException;
import ru.ylab.application.exception.UsernameAlreadyExistsException;
import ru.ylab.application.in.RegisterUser;
import ru.ylab.application.mapper.UserMapper;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.User;
import ru.ylab.aspect.annotation.Loggable;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Loggable
@Transactional
@Service
@RequiredArgsConstructor
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
    public Long execute(RegisterDto registerDto) {
        User user = userMapper.toDomain(registerDto);

        if (userRepository.isAlreadyExists(user.getUsername())) {
            throw new UsernameAlreadyExistsException(
                    "Пользователь с именем: '" + user.getUsername() + "' уже существует!"
            );
        }

        if (!(user.usernameIsValid() && user.passwordIsValid())) {
            throw new NotValidUsernameOrPasswordException("Not Valid Username Or Password!");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        UserEntity userEntity = userMapper.toEntity(user);
        return userRepository.save(userEntity);
    }

}
