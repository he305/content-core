package com.github.he305.contentcore.configuration.api;

import com.github.he305.contentcore.shared.exceptions.ApiError;
import com.github.he305.contentcore.shared.exceptions.ContentCoreException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class ApiErrorHandlerTest {

    private final ApiErrorHandler underTest = new ApiErrorHandler();

    @Test
    void handleContentCoreException() {
        String message = "test message";
        ContentCoreException exception = new ContentCoreException(message);
        ApiError expected = new ApiError(message, HttpStatus.BAD_REQUEST, LocalDateTime.now(ZoneOffset.UTC));

        ResponseEntity<Object> actual = underTest.handleContentCoreException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertTrue(actual.getBody() instanceof ApiError);

        ApiError response = (ApiError) actual.getBody();
        assertEquals(expected.getMessage(), response.getMessage());
        assertEquals(expected.getHttpStatus(), response.getHttpStatus());
    }
}