package com.agronomics.farmersserver.services;

public class PostsNotAvaiableException extends RuntimeException{
    public PostsNotAvaiableException(String message) {
        super(message);
    }
}