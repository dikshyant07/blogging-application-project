package com.blogging.application.bloggingProject.service;

import com.blogging.application.bloggingProject.dtos.ApiResponse;
import com.blogging.application.bloggingProject.dtos.CategoryCreationDto;
import com.blogging.application.bloggingProject.dtos.CategoryCreationResponseDto;
import com.blogging.application.bloggingProject.exceptions.DuplicateCategoryException;
import com.blogging.application.bloggingProject.mappers.CategoryMapper;
import com.blogging.application.bloggingProject.models.Category;
import com.blogging.application.bloggingProject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public ApiResponse<CategoryCreationResponseDto> createCategory(CategoryCreationDto categoryCreationDto) {
        if (categoryRepository.existsByName(categoryCreationDto.getName())) {
            throw new DuplicateCategoryException("The category with this name already exists");
        }
        Category category = categoryMapper.toCategory(categoryCreationDto);
        Category savedCategory = categoryRepository.save(category);

        return ApiResponse.<CategoryCreationResponseDto>builder()
                .status(true)
                .httpStatus(HttpStatus.CREATED)
                .message("Successfully created the new category " + categoryCreationDto.getName())
                .response(categoryMapper.toDto(savedCategory))
                .build();
    }

    public ApiResponse<String> deleteCategory(String name) {
        int noOfRowsDeleted = categoryRepository.deleteByName(name);
        return ApiResponse.<String>builder()
                .status(true)
                .httpStatus(HttpStatus.OK)
                .message("Successfully completed the deletion operation")
                .response("The number of rows deleted is " + noOfRowsDeleted)
                .build();
    }
}
