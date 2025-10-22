package com.blogging.application.bloggingProject.controllers;

import com.blogging.application.bloggingProject.dtos.ApiResponse;
import com.blogging.application.bloggingProject.dtos.SignupDto;
import com.blogging.application.bloggingProject.dtos.SignupResponseDto;
import com.blogging.application.bloggingProject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
    private final UserService userService;

    @Autowired
    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponseDto>> createUser(@Valid @RequestBody SignupDto signupDto) {
        ApiResponse<SignupResponseDto> accounCreationApiResponse = userService.createAccount(signupDto);
        return new ResponseEntity<>(accounCreationApiResponse, accounCreationApiResponse.getHttpStatus());

    }
}
