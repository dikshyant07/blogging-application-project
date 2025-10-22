package com.blogging.application.bloggingProject.dtos;

import com.blogging.application.bloggingProject.enums.Gender;
import com.blogging.application.bloggingProject.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupResponseDto {
    private Long userId;
    private String name;
    private int age;
    private Gender gender;
    private String email;
    private Role role;
}
