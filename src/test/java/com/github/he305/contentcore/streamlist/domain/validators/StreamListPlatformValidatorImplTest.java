package com.github.he305.contentcore.streamlist.domain.validators;

import com.github.he305.contentcore.streamlist.domain.model.values.StreamListPlatform;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamListPlatformValidatorImplTest {

    private final StreamListPlatformValidatorImpl underTest = new StreamListPlatformValidatorImpl();

    @Test
    void isStreamChannel_false() {
        ContentAccountPlatform platform = ContentAccountPlatform.TWITTER;
        boolean expected = false;
        boolean actual = underTest.isStreamChannel(platform);
        assertEquals(expected, actual);
    }

    @Test
    void isStreamChannel_true() {
        ContentAccountPlatform platform = ContentAccountPlatform.TWITCH;
        boolean expected = true;
        boolean actual = underTest.isStreamChannel(platform);
        assertEquals(expected, actual);
    }

    @Test
    void confirmAllExists() {
        Arrays.stream(StreamListPlatform.values()).forEach(
                value -> assertDoesNotThrow(() -> ContentAccountPlatform.valueOf(value.name()))
        );
    }
}