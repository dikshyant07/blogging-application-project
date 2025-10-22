package com.blogging.application.bloggingProject.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class TokenRefreshDto {
    @NotNull(message = "Please provide valid token")
    private String token;
}
