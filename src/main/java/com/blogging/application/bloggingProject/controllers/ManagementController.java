package com.blogging.application.bloggingProject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/management")
public class ManagementController {
    @GetMapping("/greet")
    public ResponseEntity<String> greetManager() {
        return new ResponseEntity<>("Welcome back manager!", HttpStatus.OK);
    }
}
