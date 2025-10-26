package com.blogging.application.bloggingProject.dtos;

import com.blogging.application.bloggingProject.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreationResponseDto {
    private Long id;
    private String title;
    private String content;
    private Status status;
    private String createBy;
    private LocalDateTime createAt;
}
