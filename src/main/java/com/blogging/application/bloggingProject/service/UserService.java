package com.blogging.application.bloggingProject.service;

import com.blogging.application.bloggingProject.dtos.ApiResponse;
import com.blogging.application.bloggingProject.dtos.SignupDto;
import com.blogging.application.bloggingProject.dtos.SignupResponseDto;
import com.blogging.application.bloggingProject.exceptions.UserAlreadyExistsException;
import com.blogging.application.bloggingProject.mappers.UserMapper;
import com.blogging.application.bloggingProject.models.User;
import com.blogging.application.bloggingProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.blogging.application.bloggingProject.enums.Role.USER;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public ApiResponse<SignupResponseDto> createAccount(SignupDto signupDto) {
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new UserAlreadyExistsException("User with this username already exists");
        }
        String encodedPassword = passwordEncoder.encode(signupDto.getPassword());
        User user = userMapper.toUser(signupDto);
        user.setPassword(encodedPassword);
        user.setRole(USER);
        User savedUser = userRepository.save(user);
        return ApiResponse.<SignupResponseDto>builder()
                .status(true)
                .httpStatus(HttpStatus.CREATED)
                .message("Successfully created an account for the email " + signupDto.getEmail())
                .response(userMapper.toDto(savedUser))
                .build();
    }
}
