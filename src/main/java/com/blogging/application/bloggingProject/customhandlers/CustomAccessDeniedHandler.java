package com.blogging.application.bloggingProject.customhandlers;

import com.blogging.application.bloggingProject.dtos.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "procected rout can not be accessed with your authority");
        problemDetail.setProperty("Exception Time", LocalDateTime.now());
        ApiResponse<ProblemDetail> apiResponse = ApiResponse.<ProblemDetail>builder()
                .status(false)
                .httpStatus(HttpStatus.FORBIDDEN)
                .message("You are not allowed to access this route")
                .response(problemDetail)
                .build();
        response.setContentType("application/json");
        response.setStatus(403);
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }
}
