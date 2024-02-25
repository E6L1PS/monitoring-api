package ru.ylab.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ylab.adapters.in.web.dto.LoginDto;
import ru.ylab.adapters.in.web.dto.RegisterDto;
import ru.ylab.adapters.out.persistence.entity.UserEntity;
import ru.ylab.domain.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDomain(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toDomain(LoginDto loginDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    User toDomain(RegisterDto registerUser);

    UserEntity toEntity(User user);

}
