package com.blogging.application.bloggingProject.customhandlers;

import com.blogging.application.bloggingProject.dtos.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "invalid jwt token");
        problemDetail.setProperty("Exception Time", LocalDateTime.now());
        ApiResponse<ProblemDetail> apiResponse = ApiResponse.<ProblemDetail>builder()
                .status(false)
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message("Invalid Jwt Token")
                .response(problemDetail)
                .build();
        response.setContentType("application/json");
        response.setStatus(403);
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }
}
