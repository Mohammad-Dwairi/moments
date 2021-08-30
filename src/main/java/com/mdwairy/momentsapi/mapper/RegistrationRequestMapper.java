package com.mdwairy.momentsapi.mapper;

import com.mdwairy.momentsapi.registration.RegistrationRequest;
import com.mdwairy.momentsapi.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegistrationRequestMapper {

    RegistrationRequestMapper INSTANCE = Mappers.getMapper(RegistrationRequestMapper.class);
    
    @Mapping(target = "isAccountLocked", constant = "false")
    @Mapping(target = "isAccountEnabled", constant = "true")
    @Mapping(target = "role", constant = "ROLE_USER")
    User registrationRequestToUser(RegistrationRequest registrationRequest);
}
