package com.github.he305.contentcore.configuration.api;

import com.github.he305.contentcore.shared.exceptions.ApiError;
import com.github.he305.contentcore.shared.exceptions.ContentCoreException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler(value = {ContentCoreException.class})
    public ResponseEntity<Object> handleContentCoreException(ContentCoreException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError error = new ApiError(
                ex.getMessage(),
                status,
                LocalDateTime.now(ZoneOffset.UTC)
        );
        return new ResponseEntity<>(error, status);
    }
}
