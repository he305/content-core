package com.github.he305.contentcore.contentaccount.application.services.urlservices;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.shared.exceptions.ContentCoreArgumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlContentAccountFactoryImplTest {

    @Mock
    private UrlContentAccountService service;

    UrlContentAccountFactoryImpl init() {
        when(service.getType()).thenReturn(Platform.TWITCH);
        UrlContentAccountFactoryImpl underTest = new UrlContentAccountFactoryImpl(
                List.of(service)
        );

        assertDoesNotThrow(underTest::initServiceMap);
        return underTest;
    }

    @Test
    void getUrlContentAccountService_noServiceProvider_shouldThrow() {
        UrlContentAccountFactoryImpl underTest = init();
        Platform platform = Platform.GOODGAME;
        assertThrows(ContentCoreArgumentException.class, () ->
                underTest.getUrlContentAccountService(platform));
    }

    @Test
    void getUrlContentAccountService_valid() {
        UrlContentAccountFactoryImpl underTest = init();
        Platform platform = Platform.TWITCH;
        UrlContentAccountService actual = underTest.getUrlContentAccountService(platform);
        assertEquals(service, actual);
    }
}