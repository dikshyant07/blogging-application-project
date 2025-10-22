package com.blogging.application.bloggingProject.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @Email(message = "Please enter valid email")
    @NotBlank(message = "Please enter valid email")
    private String email;
    @NotBlank(message = "Please provide non empty ,valid password")
    private String password;
}
