package com.github.he305.contentcore.stream.domain.model;

import com.github.he305.contentcore.stream.domain.exceptions.NoLiveStreamFoundException;
import com.github.he305.contentcore.stream.domain.exceptions.StreamChannelIsAlreadyFrozenException;
import com.github.he305.contentcore.stream.domain.exceptions.StreamChannelIsAlreadyObservableException;
import com.github.he305.contentcore.stream.domain.model.entities.Stream;
import com.github.he305.contentcore.stream.domain.model.entities.StreamData;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelStatus;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StreamChannelTest {

    @Test
    void create() {
        UUID id = UUID.randomUUID();
        StreamChannelPlatform platform = StreamChannelPlatform.TWITCH;
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), platform);
        assertEquals(new StreamChannelContentAccountId(id), streamChannel.getStreamChannelContentAccountId());
        assertEquals(StreamChannelStatus.FROZEN, streamChannel.getStatus());
        assertEquals(platform, streamChannel.getPlatform());
        assertTrue(streamChannel.getStreams().isEmpty());
    }

    @Test
    void startObserving_valid() {
        UUID id = UUID.randomUUID();
        StreamChannelPlatform platform = StreamChannelPlatform.TWITCH;
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), platform);
        assertEquals(StreamChannelStatus.FROZEN, streamChannel.getStatus());
        assertDoesNotThrow(streamChannel::startObserving);
    }

    @Test
    void startObserving_alreadyObserving() {
        UUID id = UUID.randomUUID();
        StreamChannelPlatform platform = StreamChannelPlatform.TWITCH;
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), platform);
        assertEquals(StreamChannelStatus.FROZEN, streamChannel.getStatus());
        assertDoesNotThrow(streamChannel::startObserving);
        assertThrows(StreamChannelIsAlreadyObservableException.class, streamChannel::startObserving);
    }

    @Test
    void freeze_alreadyFrozen() {
        UUID id = UUID.randomUUID();
        StreamChannelPlatform platform = StreamChannelPlatform.TWITCH;
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), platform);
        assertEquals(StreamChannelStatus.FROZEN, streamChannel.getStatus());
        assertThrows(StreamChannelIsAlreadyFrozenException.class, streamChannel::freeze);
    }

    @Test
    void freeze_valid() {
        UUID id = UUID.randomUUID();
        StreamChannelPlatform platform = StreamChannelPlatform.TWITCH;
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), platform);
        assertEquals(StreamChannelStatus.FROZEN, streamChannel.getStatus());
        assertDoesNotThrow(streamChannel::startObserving);
        assertDoesNotThrow(streamChannel::freeze);
    }

    @Test
    void addStreamData_newStream() {
        UUID id = UUID.randomUUID();
        StreamChannelPlatform platform = StreamChannelPlatform.TWITCH;
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), platform);
        assertTrue(streamChannel.getStreams().isEmpty());
        StreamData data = new StreamData("name", "title", 10, LocalDateTime.now());

        streamChannel.addStreamData(data);

        assertEquals(1, streamChannel.getStreams().size());
        assertEquals(1, streamChannel.getStreams().get(0).getStreamDataList().size());
        StreamData savedData = streamChannel.getStreams().get(0).getStreamDataList().get(0);
        assertEquals(data.getGameName(), savedData.getGameName());
        assertEquals(data.getTitle(), savedData.getTitle());
        assertEquals(data.getViewerCount(), savedData.getViewerCount());
        assertEquals(data.getStreamDataTime(), savedData.getStreamDataTime());

        Collection<Object> events = streamChannel.getEvents();
        assertEquals(2, events.size());
    }

    @Test
    void addStreamData_existingStream() {
        UUID id = UUID.randomUUID();
        StreamChannelPlatform platform = StreamChannelPlatform.TWITCH;
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), platform);
        assertTrue(streamChannel.getStreams().isEmpty());
        StreamData data = new StreamData("name", "title", 10, LocalDateTime.now());
        streamChannel.addStreamData(data);

        StreamData newData = new StreamData("game", "title", 11, LocalDateTime.now());
        streamChannel.addStreamData(newData);

        assertEquals(1, streamChannel.getStreams().size());
        assertEquals(2, streamChannel.getStreams().get(0).getStreamDataList().size());
        StreamData savedData = streamChannel.getStreams().get(0).getStreamDataList().get(1);
        assertEquals(newData.getGameName(), savedData.getGameName());
        assertEquals(newData.getTitle(), savedData.getTitle());
        assertEquals(newData.getViewerCount(), savedData.getViewerCount());
        assertEquals(newData.getStreamDataTime(), savedData.getStreamDataTime());

        StreamData lastData = new StreamData("game", "title1", 11, LocalDateTime.now());
        streamChannel.addStreamData(lastData);
        assertEquals(3, streamChannel.getStreams().get(0).getStreamDataList().size());
    }

    @Test
    void endStream_valid() {
        UUID id = UUID.randomUUID();
        StreamChannelPlatform platform = StreamChannelPlatform.TWITCH;
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), platform);
        assertTrue(streamChannel.getStreams().isEmpty());
        StreamData data = new StreamData("name", "title", 10, LocalDateTime.now());
        streamChannel.addStreamData(data);

        LocalDateTime now = LocalDateTime.now();
        assertDoesNotThrow(() -> streamChannel.endStream(now));
        assertTrue(streamChannel.getStreams().stream().noneMatch(Stream::isLive));
    }

    @Test
    void endStream_noLiveStream() {
        UUID id = UUID.randomUUID();
        StreamChannelPlatform platform = StreamChannelPlatform.TWITCH;
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), platform);
        assertTrue(streamChannel.getStreams().isEmpty());
        StreamData data = new StreamData("name", "title", 10, LocalDateTime.now());
        streamChannel.addStreamData(data);

        LocalDateTime now = LocalDateTime.now();
        assertDoesNotThrow(() -> streamChannel.endStream(now));
        assertTrue(streamChannel.getStreams().stream().noneMatch(Stream::isLive));

        assertThrows(NoLiveStreamFoundException.class, () ->
                streamChannel.endStream(now));
    }
}