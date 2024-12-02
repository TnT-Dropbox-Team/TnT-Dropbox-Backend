package com.tntteam.tntdropbox.exceptions.resourceNotFound;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
