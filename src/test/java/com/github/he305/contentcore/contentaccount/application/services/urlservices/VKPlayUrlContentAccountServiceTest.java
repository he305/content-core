package com.github.he305.contentcore.contentaccount.application.services.urlservices;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VKPlayUrlContentAccountServiceTest {

    private final VKPlayUrlContentAccountService underTest = new VKPlayUrlContentAccountService();

    @Test
    void getUrl() {
        String accountName = "test";
        String expected = "https://vkplay.live/test";

        String actual = underTest.getUrl(accountName);
        assertEquals(expected, actual);
    }

    @Test
    void getType() {
        Platform expected = Platform.VKPLAY;
        Platform actual = underTest.getType();
        assertEquals(expected, actual);
    }
}