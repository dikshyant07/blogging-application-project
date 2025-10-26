package com.blogging.application.bloggingProject.exceptions;

public class PostDoesNotExistsException extends RuntimeException {
    public PostDoesNotExistsException(String message) {
        super(message);
    }
}
