package com.github.he305.contentcore.stream.domain.mapper;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.stream.domain.exceptions.ErrorCreatingStreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class StreamChannelPlatformMapperImplTest {

    private StreamChannelPlatformMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new StreamChannelPlatformMapperImpl();
    }

    @Test
    void getStreamChannelPlatform_error() {
        Platform platform = Mockito.mock(Platform.class);
        Mockito.when(platform.name()).thenReturn("not in a enum");
        assertThrows(ErrorCreatingStreamChannelPlatform.class, () ->
                underTest.getStreamChannelPlatform(platform));
    }

    @Test
    void getStreamChannelPlatform_valid() {
        Platform platform = Platform.TWITCH;
        StreamChannelPlatform expected = StreamChannelPlatform.TWITCH;
        StreamChannelPlatform actual = underTest.getStreamChannelPlatform(platform);
        assertEquals(expected, actual);
    }
}