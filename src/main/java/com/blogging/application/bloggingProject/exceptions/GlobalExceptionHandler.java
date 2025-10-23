package com.blogging.application.bloggingProject.exceptions;

import com.blogging.application.bloggingProject.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ProblemDetail>> handleRuntimeExceptions(Exception e) {
        HttpStatus httpStatus;
        if (e instanceof UserAlreadyExistsException) httpStatus = HttpStatus.CONFLICT;
        else if (e instanceof InvalidJwtException) httpStatus = HttpStatus.UNAUTHORIZED;
        else httpStatus = HttpStatus.BAD_REQUEST;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, e.getMessage());
        problemDetail.setProperty("Exception Time", LocalDateTime.now());
        ApiResponse<ProblemDetail> apiResponse = ApiResponse.<ProblemDetail>builder()
                .status(false)
                .httpStatus(httpStatus)
                .message("Exception occurred!")
                .response(problemDetail)
                .build();
        return new ResponseEntity<>(apiResponse, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ProblemDetail>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, null);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, String> errorMap = new HashMap<>();
        fieldErrors.forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        problemDetail.setProperty("Errors", errorMap);

        ApiResponse<ProblemDetail> apiResponse = ApiResponse.<ProblemDetail>builder()
                .status(false)
                .httpStatus(HttpStatus.NOT_FOUND)
                .message("method argument not valid exception occured")
                .response(problemDetail)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
