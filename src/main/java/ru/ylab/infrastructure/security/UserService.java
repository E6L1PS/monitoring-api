package ru.ylab.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.mapper.UserMapper;
import ru.ylab.application.out.UserRepository;

/**
 * Создан: 21.02.2024.
 *
 * @author Pesternikov Danil
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.getByUsername(username);
        return userMapper.toDomain(userEntity);
    }

}
