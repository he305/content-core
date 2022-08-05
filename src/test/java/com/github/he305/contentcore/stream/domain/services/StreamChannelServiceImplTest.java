package com.github.he305.contentcore.stream.domain.services;

import com.github.he305.contentcore.contentaccount.domain.events.ContentAccountBecameActiveEvent;
import com.github.he305.contentcore.contentaccount.domain.events.ContentAccountBecameFrozenEvent;
import com.github.he305.contentcore.contentaccount.domain.events.NewContentAccountAddedEvent;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.stream.domain.exceptions.ErrorCreatingStreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.exceptions.StreamChannelWithContentAccountIdNotFoundException;
import com.github.he305.contentcore.stream.domain.mapper.StreamChannelPlatformMapper;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelStatus;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
import com.github.he305.contentcore.stream.domain.repository.StreamChannelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StreamChannelServiceImplTest {

    @Mock
    private StreamChannelPlatformMapper streamChannelPlatformMapper;
    @Mock
    private StreamChannelRepository streamChannelRepository;

    @InjectMocks
    private StreamChannelServiceImpl underTest;

    @Test
    void handleNewContentAccountAddedEvent_mapperError() {
        NewContentAccountAddedEvent event = new NewContentAccountAddedEvent(UUID.randomUUID(), Platform.TWITCH);
        Mockito.when(streamChannelPlatformMapper.getStreamChannelPlatform(event.getPlatform())).thenThrow(ErrorCreatingStreamChannelPlatform.class);
        assertDoesNotThrow(() -> underTest.handleNewContentAccountAddedEvent(event));
    }

    @Test
    void handleNewContentAccountAddedEvent_valid() {
        NewContentAccountAddedEvent event = new NewContentAccountAddedEvent(UUID.randomUUID(), Platform.TWITCH);
        Mockito.when(streamChannelPlatformMapper.getStreamChannelPlatform(event.getPlatform())).thenReturn(StreamChannelPlatform.TWITCH);
        assertDoesNotThrow(() -> underTest.handleNewContentAccountAddedEvent(event));
    }

    @Test
    void handleContentAccountBecameActiveEvent() {
        UUID id = UUID.randomUUID();
        ContentAccountBecameActiveEvent event = new ContentAccountBecameActiveEvent(id);
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), StreamChannelPlatform.TWITCH);
        assertEquals(StreamChannelStatus.FROZEN, streamChannel.getStatus());
        Mockito.when(streamChannelRepository.getByContentAccountId(new StreamChannelContentAccountId(id))).thenReturn(streamChannel);
        assertDoesNotThrow(() -> underTest.handleContentAccountBecameActiveEvent(event));
        assertEquals(StreamChannelStatus.OBSERVABLE, streamChannel.getStatus());
    }

    @Test
    void handleContentAccountBecameActiveEvent_notFound() {
        UUID id = UUID.randomUUID();
        ContentAccountBecameActiveEvent event = new ContentAccountBecameActiveEvent(id);
        Mockito.when(streamChannelRepository.getByContentAccountId(new StreamChannelContentAccountId(id))).thenThrow(StreamChannelWithContentAccountIdNotFoundException.class);
        assertDoesNotThrow(() -> underTest.handleContentAccountBecameActiveEvent(event));
    }

    @Test
    void handleContentAccountBecameFrozenEvent() {
        UUID id = UUID.randomUUID();
        ContentAccountBecameFrozenEvent event = new ContentAccountBecameFrozenEvent(id);
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), StreamChannelPlatform.TWITCH);
        streamChannel.startObserving();
        assertEquals(StreamChannelStatus.OBSERVABLE, streamChannel.getStatus());
        Mockito.when(streamChannelRepository.getByContentAccountId(new StreamChannelContentAccountId(id))).thenReturn(streamChannel);
        assertDoesNotThrow(() -> underTest.handleContentAccountBecameFrozenEvent(event));
        assertEquals(StreamChannelStatus.FROZEN, streamChannel.getStatus());

    }

    @Test
    void handleContentAccountBecameFrozenEvent_notFound() {
        UUID id = UUID.randomUUID();
        ContentAccountBecameFrozenEvent event = new ContentAccountBecameFrozenEvent(id);
        Mockito.when(streamChannelRepository.getByContentAccountId(new StreamChannelContentAccountId(id))).thenThrow(StreamChannelWithContentAccountIdNotFoundException.class);
        assertDoesNotThrow(() -> underTest.handleContentAccountBecameFrozenEvent(event));
    }
}