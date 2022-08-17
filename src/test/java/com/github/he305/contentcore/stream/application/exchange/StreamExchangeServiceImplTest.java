package com.github.he305.contentcore.stream.application.exchange;

import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.entities.StreamData;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
import com.github.he305.contentcore.stream.domain.repository.StreamChannelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StreamExchangeServiceImplTest {

    @Mock
    private StreamChannelRepository streamChannelRepository;

    @InjectMocks
    private StreamExchangeServiceImpl underTest;

    @Test
    void getIsLive() {
        UUID id = UUID.randomUUID();
        StreamChannelPlatform platform = StreamChannelPlatform.TWITCH;
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), platform);
        assertTrue(streamChannel.getStreams().isEmpty());
        StreamData data = new StreamData("name", "title", 10, LocalDateTime.now(ZoneOffset.UTC));
        streamChannel.addStreamData(data);

        Mockito.when(streamChannelRepository.getByContentAccountId(new StreamChannelContentAccountId(id))).thenReturn(streamChannel);
        boolean expected = true;
        boolean actual = underTest.getIsLive(id);
        assertEquals(expected, actual);
    }

    @Test
    void getLastLiveStreamData_noLiveStreams() {
        UUID id = UUID.randomUUID();
        StreamChannelPlatform platform = StreamChannelPlatform.TWITCH;
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), platform);
        assertTrue(streamChannel.getStreams().isEmpty());
        Mockito.when(streamChannelRepository.getByContentAccountId(new StreamChannelContentAccountId(id))).thenReturn(streamChannel);

        StreamData actual = underTest.getLastLiveStreamData(id);
        assertNull(actual);
    }

    @Test
    void getLastLiveStreamData_valid() {
        UUID id = UUID.randomUUID();
        StreamChannelPlatform platform = StreamChannelPlatform.TWITCH;
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), platform);
        assertTrue(streamChannel.getStreams().isEmpty());
        StreamData data = new StreamData("name", "title", 10, LocalDateTime.now(ZoneOffset.UTC));
        streamChannel.addStreamData(data);

        Mockito.when(streamChannelRepository.getByContentAccountId(new StreamChannelContentAccountId(id))).thenReturn(streamChannel);

        StreamData actual = underTest.getLastLiveStreamData(id);
        assertEquals(data, actual);
    }
}