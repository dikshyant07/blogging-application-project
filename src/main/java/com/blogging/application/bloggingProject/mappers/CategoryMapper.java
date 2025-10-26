package com.blogging.application.bloggingProject.mappers;

import com.blogging.application.bloggingProject.dtos.CategoryCreationDto;
import com.blogging.application.bloggingProject.dtos.CategoryCreationResponseDto;
import com.blogging.application.bloggingProject.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "id", ignore = true)
    Category toCategory(CategoryCreationDto categoryCreationDto);

    CategoryCreationResponseDto toDto(Category category);
}
