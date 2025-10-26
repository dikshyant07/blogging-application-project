package com.blogging.application.bloggingProject.dtos;

import com.blogging.application.bloggingProject.enums.Status;
import com.blogging.application.bloggingProject.validators.TagsValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreationDto {
    @Email(message = "please provide valid email address")
    private String userEmail;
    @NotBlank(message = "Title should not be null or blank")
    private String title;
    @NotBlank(message = "Content should not be null or blank")
    private String content;
    @NotNull(message = "Status can not be null")
    private Status status;
    @TagsValidator
    private List<String> tags;
    @NotBlank(message = "Category should not be null or blank")
    private String category;
}
