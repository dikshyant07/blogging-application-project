package com.blogging.application.bloggingProject.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
}
