package com.blogging.application.bloggingProject.service;

import com.blogging.application.bloggingProject.dtos.ApiResponse;
import com.blogging.application.bloggingProject.dtos.RefreshTokenResponseDto;
import com.blogging.application.bloggingProject.dtos.TokenRefreshDto;
import com.blogging.application.bloggingProject.exceptions.RefreshTokenExpiredException;
import com.blogging.application.bloggingProject.exceptions.TokenDoesNotExistsException;
import com.blogging.application.bloggingProject.exceptions.UserDoesNotExistsException;
import com.blogging.application.bloggingProject.models.RefreshToken;
import com.blogging.application.bloggingProject.models.User;
import com.blogging.application.bloggingProject.repository.RefreshTokenRepository;
import com.blogging.application.bloggingProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    private RefreshToken generateRefreshToken(User user) {
        return RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiry(Instant.now().plusSeconds(24 * 60 * 60))
                .user(user)
                .build();

    }

    public String createRefreshToken(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UserDoesNotExistsException("User with his email does not exists ,so failed to generate refresh token"));
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByUser(user);
        refreshTokenOptional.ifPresent(refreshTokenRepository::delete);
        RefreshToken refreshToken = generateRefreshToken(user);
        return refreshToken.getToken();
    }

    public ApiResponse<RefreshTokenResponseDto> refreshTheToken(TokenRefreshDto tokenRefreshDto) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(tokenRefreshDto.getToken()).orElseThrow(() -> new TokenDoesNotExistsException("Token does not exists,please login to get the token"));
        if (refreshToken.getExpiry().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenExpiredException("Refresh token is already expired,please login to get new one");
        }
        RefreshToken newRefreshToken = generateRefreshToken(refreshToken.getUser());
        refreshTokenRepository.delete(refreshToken);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(newRefreshToken);
        RefreshTokenResponseDto responseDto = RefreshTokenResponseDto.builder()
                .accessToken("Access Token")
                .refreshToken(savedRefreshToken.getToken())
                .build();
        return ApiResponse.<RefreshTokenResponseDto>builder()
                .status(true)
                .httpStatus(HttpStatus.CREATED)
                .message("New refresh token dnd access token assigned successfully")
                .response(responseDto)
                .build();
    }
}
