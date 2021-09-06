package com.mdwairy.momentsapi.mapper;

import com.mdwairy.momentsapi.dto.UserDto;
import com.mdwairy.momentsapi.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "infoId", source = "userInfo.id")
    UserDto userToUserDto(User user);
}
