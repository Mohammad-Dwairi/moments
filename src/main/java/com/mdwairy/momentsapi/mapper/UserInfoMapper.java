package com.mdwairy.momentsapi.mapper;

import com.mdwairy.momentsapi.dto.UserInfoDTO;
import com.mdwairy.momentsapi.userinfo.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserInfoMapper {

    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

    UserInfoDTO userInfoToUserInfoDto(UserInfo info);
}
