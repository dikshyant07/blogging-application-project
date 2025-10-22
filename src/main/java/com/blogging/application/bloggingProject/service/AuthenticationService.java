package com.blogging.application.bloggingProject.service;

import com.blogging.application.bloggingProject.dtos.ApiResponse;
import com.blogging.application.bloggingProject.dtos.LoginRequestDto;
import com.blogging.application.bloggingProject.dtos.LoginResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    public ApiResponse<LoginResponseDto> login(LoginRequestDto requestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));
            LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                    .accessToken(jwtService.generateJwt(requestDto.getEmail()))
                    .refreshToken(refreshTokenService.createRefreshToken(requestDto.getEmail()))
                    .build();
            return ApiResponse.<LoginResponseDto>builder()
                    .status(true)
                    .httpStatus(HttpStatus.OK)
                    .message("Successfully authenticated")
                    .response(loginResponseDto)
                    .build();
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Please provide valid email or password to login");
        }

    }

}
