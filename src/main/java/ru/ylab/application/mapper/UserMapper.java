package ru.ylab.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ylab.adapters.in.web.dto.LoginModel;
import ru.ylab.adapters.in.web.dto.RegisterModel;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.domain.model.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserEntity userEntity);

    @Mapping(target = "role", ignore = true)
    User toUser(LoginModel loginModel);

    @Mapping(target = "role", constant = "USER")
    User toUser(RegisterModel registerUser);

    @Mapping(target = "id", ignore = true)
    UserEntity userToUserEntity(User user);
}
