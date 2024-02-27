package ru.ylab.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.mapper.UserMapper;
import ru.ylab.application.out.UserRepository;
import ru.ylab.aspect.annotation.Auditable;
import ru.ylab.aspect.annotation.Loggable;

/**
 * Создан: 27.02.2024.
 *
 * @author Pesternikov Danil
 */
@Auditable
@Loggable
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.getByUsername(username);
        return userMapper.toDomain(userEntity);
    }

}
