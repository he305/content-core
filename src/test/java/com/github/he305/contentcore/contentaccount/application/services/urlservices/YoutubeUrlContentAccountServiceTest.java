package com.github.he305.contentcore.contentaccount.application.services.urlservices;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YoutubeUrlContentAccountServiceTest {

    private final YoutubeUrlContentAccountService underTest = new YoutubeUrlContentAccountService();

    @Test
    void getUrl() {
        String accountName = "test";
        String expected = "https://www.youtube.com/channel/test";
        String actual = underTest.getUrl(accountName);
        assertEquals(expected, actual);
    }

    @Test
    void getType() {
        Platform expected = Platform.YOUTUBE;
        Platform actual = underTest.getType();
        assertEquals(expected, actual);
    }
}