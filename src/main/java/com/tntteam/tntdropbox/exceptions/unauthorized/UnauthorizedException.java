package com.tntteam.tntdropbox.exceptions.unauthorized;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
