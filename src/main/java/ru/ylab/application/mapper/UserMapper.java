package ru.ylab.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.application.model.RegisterModel;
import ru.ylab.domain.model.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserEntity userEntity);

    @Mapping(target = "role", constant = "USER")
    User toUser(RegisterModel registerUser);

    UserEntity userToUserEntity(User user);
}
