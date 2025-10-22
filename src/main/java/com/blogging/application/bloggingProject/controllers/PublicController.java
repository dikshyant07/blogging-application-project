package com.blogging.application.bloggingProject.controllers;

import com.blogging.application.bloggingProject.dtos.*;
import com.blogging.application.bloggingProject.service.AuthenticationService;
import com.blogging.application.bloggingProject.service.RefreshTokenService;
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
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public PublicController(UserService userService, AuthenticationService authenticationService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponseDto>> createUser(@Valid @RequestBody SignupDto signupDto) {
        ApiResponse<SignupResponseDto> accounCreationApiResponse = userService.createAccount(signupDto);
        return new ResponseEntity<>(accounCreationApiResponse, accounCreationApiResponse.getHttpStatus());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        ApiResponse<LoginResponseDto> apiResponse = authenticationService.login(loginRequestDto);
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshTokenResponseDto>> refreshTheToken(@Valid @RequestBody TokenRefreshDto tokenRefreshDto) {
        ApiResponse<RefreshTokenResponseDto> refreshTokenResponseDtoApiResponse = refreshTokenService.refreshTheToken(tokenRefreshDto);
        return new ResponseEntity<>(refreshTokenResponseDtoApiResponse, refreshTokenResponseDtoApiResponse.getHttpStatus());

    }
}
