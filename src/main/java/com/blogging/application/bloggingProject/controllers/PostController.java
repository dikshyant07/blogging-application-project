package com.blogging.application.bloggingProject.controllers;

import com.blogging.application.bloggingProject.dtos.ApiResponse;
import com.blogging.application.bloggingProject.dtos.PostCreationDto;
import com.blogging.application.bloggingProject.dtos.PostCreationResponseDto;
import com.blogging.application.bloggingProject.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create-post")
    public ResponseEntity<ApiResponse<PostCreationResponseDto>> createPost(@Valid @RequestBody PostCreationDto postCreationDto) {
        ApiResponse<PostCreationResponseDto> postCreationResponse = postService.createPost(postCreationDto);
        return new ResponseEntity<>(postCreationResponse, postCreationResponse.getHttpStatus());
    }
}
