package com.mdwairy.momentsapi.mapper;

import com.mdwairy.momentsapi.registration.RegistrationRequest;
import com.mdwairy.momentsapi.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegistrationRequestMapper {

    RegistrationRequestMapper INSTANCE = Mappers.getMapper(RegistrationRequestMapper.class);

    User registrationRequestToUser(RegistrationRequest registrationRequest);
}
