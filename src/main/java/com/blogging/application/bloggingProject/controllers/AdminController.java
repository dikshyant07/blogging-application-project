package com.blogging.application.bloggingProject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @GetMapping("/greet")
    public ResponseEntity<String> greetAdmin() {
        return new ResponseEntity<>("Welcome admin!", HttpStatus.OK);

    }
}
