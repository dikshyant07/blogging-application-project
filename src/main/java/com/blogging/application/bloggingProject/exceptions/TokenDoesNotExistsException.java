package com.blogging.application.bloggingProject.exceptions;

public class TokenDoesNotExistsException extends RuntimeException {
    public TokenDoesNotExistsException(String message) {
        super(message);
    }
}
