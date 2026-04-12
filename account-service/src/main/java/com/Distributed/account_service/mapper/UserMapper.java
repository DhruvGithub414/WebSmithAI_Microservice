package com.Distributed.account_service.mapper;


import com.Distributed.account_service.dto.auth.SignupRequest;
import com.Distributed.account_service.dto.auth.UserProfileResponse;
import com.Distributed.account_service.entity.User;
import com.Distributed.common_lib.dto.UserDto;
import com.Distributed.common_lib.security.JwtUserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(SignupRequest signupRequest);

    @Mapping(source = "userId", target = "id")
    UserProfileResponse toUserProfileResponse(JwtUserPrincipal user);

    UserDto toUserDto(User user);

}
