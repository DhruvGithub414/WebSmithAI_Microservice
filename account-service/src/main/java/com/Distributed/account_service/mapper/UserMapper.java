package com.Distributed.account_service.mapper;


import com.Distributed.account_service.dto.auth.SignupRequest;
import com.Distributed.account_service.dto.auth.UserProfileResponse;
import com.Distributed.account_service.entity.User;
import com.Distributed.common_lib.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(SignupRequest signupRequest);

    UserProfileResponse toUserProfileResponse(User user);

    UserDto toUserDto(User user);
}
