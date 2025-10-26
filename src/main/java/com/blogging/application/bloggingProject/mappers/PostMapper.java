package com.blogging.application.bloggingProject.mappers;

import com.blogging.application.bloggingProject.dtos.PostCreationDto;
import com.blogging.application.bloggingProject.dtos.PostCreationResponseDto;
import com.blogging.application.bloggingProject.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    Post toPost(PostCreationDto postCreationDto);

    @Mapping(target = "createBy", source = "createdBy")
    @Mapping(target = "createAt", source = "createdAt")
    PostCreationResponseDto toDto(Post post);
}
