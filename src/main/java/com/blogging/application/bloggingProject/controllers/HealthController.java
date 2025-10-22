package com.blogging.application.bloggingProject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping("/check")
    public ResponseEntity<String> checkHealth() {
        return new ResponseEntity<>("Tomcat is running in 8080", HttpStatus.OK);
    }
}
