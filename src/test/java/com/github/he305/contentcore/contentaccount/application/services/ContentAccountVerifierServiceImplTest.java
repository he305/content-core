package com.github.he305.contentcore.contentaccount.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.he305.contentcore.contentaccount.domain.exceptions.ContentAccountVerifierException;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContentAccountVerifierServiceImplTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private ContentAccountVerifierServiceImpl underTest;

    @Test
    @SneakyThrows
    void verify_valid() {
        ContentAccountDetails details = new ContentAccountDetails("name", Platform.TWITCH);
        when(mapper.writeValueAsString(Mockito.any())).thenReturn("String");
        assertDoesNotThrow(() -> underTest.verify(details));
    }

    @Test
    @SneakyThrows
    void verify_badRequest_shouldThrow() {
        ContentAccountDetails details = new ContentAccountDetails("name", Platform.TWITCH);
        when(mapper.writeValueAsString(Mockito.any())).thenReturn("String");
        doThrow(HttpClientErrorException.class).when(restTemplate).exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(Void.class));
        assertThrows(ContentAccountVerifierException.class, () ->
                underTest.verify(details));
    }

    @Test
    @SneakyThrows
    void verify_resourceUnavailable_valid() {
        ContentAccountDetails details = new ContentAccountDetails("name", Platform.TWITCH);
        when(mapper.writeValueAsString(Mockito.any())).thenReturn("String");
        doThrow(ResourceAccessException.class).when(restTemplate).exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(Void.class));
        assertDoesNotThrow(() -> underTest.verify(details));
    }

    @Test
    @SneakyThrows
    void verify_serverError_valid() {
        ContentAccountDetails details = new ContentAccountDetails("name", Platform.TWITCH);
        when(mapper.writeValueAsString(Mockito.any())).thenReturn("String");
        doThrow(HttpServerErrorException.class).when(restTemplate).exchange(Mockito.anyString(), eq(HttpMethod.GET), Mockito.any(), eq(Void.class));
        assertDoesNotThrow(() -> underTest.verify(details));
    }

    @Test
    @SneakyThrows
    void verify_errorProcessingJson_valid() {
        ContentAccountDetails details = new ContentAccountDetails("name", Platform.TWITCH);
        when(mapper.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);
        assertDoesNotThrow(() -> underTest.verify(details));
    }
}