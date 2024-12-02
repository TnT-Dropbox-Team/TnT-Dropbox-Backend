package com.tntteam.tntdropbox.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ErrorResponse(HttpStatus status, String message, ZonedDateTime timestamp, String error, String path) {
}
