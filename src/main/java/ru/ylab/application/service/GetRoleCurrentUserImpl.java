package ru.ylab.application.service;

import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;
import ru.ylab.application.in.GetRoleCurrentUser;
import ru.ylab.application.out.UserRepository;
import ru.ylab.domain.model.Role;

/**
 * {@inheritDoc}
 *
 * @author Pesternikov Danil
 */
@Singleton
public class GetRoleCurrentUserImpl implements GetRoleCurrentUser {

    @Autowired
    private UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Role execute() {
        return userRepository.getCurrentRoleUser();
    }
}
