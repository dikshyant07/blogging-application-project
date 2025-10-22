package com.blogging.application.bloggingProject.mappers;

import com.blogging.application.bloggingProject.dtos.SignupDto;
import com.blogging.application.bloggingProject.dtos.SignupResponseDto;
import com.blogging.application.bloggingProject.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUser(SignupDto signupDto);

    @Mapping(source = "id", target = "userId")
    SignupResponseDto toDto(User user);
}
