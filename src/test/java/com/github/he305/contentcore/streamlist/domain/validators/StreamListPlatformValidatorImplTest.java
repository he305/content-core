package com.github.he305.contentcore.streamlist.domain.validators;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamListPlatformValidatorImplTest {

    private final StreamListPlatformValidatorImpl underTest = new StreamListPlatformValidatorImpl();

    @Test
    void isStreamChannel_false() {
        Platform platform = Platform.TWITTER;
        boolean expected = false;
        boolean actual = underTest.isStreamChannel(platform);
        assertEquals(expected, actual);
    }

    @Test
    void isStreamChannel_true() {
        Platform platform = Platform.TWITCH;
        boolean expected = true;
        boolean actual = underTest.isStreamChannel(platform);
        assertEquals(expected, actual);
    }

    @Test
    void confirmAllExists() {
        Arrays.stream(Platform.values()).forEach(
                value -> assertDoesNotThrow(() -> ContentAccountPlatform.valueOf(value.name()))
        );
    }
}