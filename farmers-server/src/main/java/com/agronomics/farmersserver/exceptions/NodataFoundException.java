package com.agronomics.farmersserver.exceptions;

public class NodataFoundException extends RuntimeException{
    public NodataFoundException(String message) {
        super(message);
    }
}
