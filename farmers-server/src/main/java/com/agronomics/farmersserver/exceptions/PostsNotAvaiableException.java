package com.agronomics.farmersserver.exceptions;

public class PostsNotAvaiableException extends RuntimeException{
    public PostsNotAvaiableException(String message) {
        super(message);
    }
}