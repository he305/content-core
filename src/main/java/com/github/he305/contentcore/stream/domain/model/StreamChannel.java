package com.github.he305.contentcore.stream.domain.model;

import com.github.he305.contentcore.shared.common.ContentAccountData;
import com.github.he305.contentcore.shared.events.NewContentAccountDataEvent;
import com.github.he305.contentcore.stream.domain.events.StreamChannelGoneOfflineEvent;
import com.github.he305.contentcore.stream.domain.events.StreamChannelIsLiveEvent;
import com.github.he305.contentcore.stream.domain.exceptions.NoLiveStreamFoundException;
import com.github.he305.contentcore.stream.domain.exceptions.StreamChannelIsAlreadyFrozenException;
import com.github.he305.contentcore.stream.domain.exceptions.StreamChannelIsAlreadyObservableException;
import com.github.he305.contentcore.stream.domain.model.entities.Stream;
import com.github.he305.contentcore.stream.domain.model.entities.StreamData;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelStatus;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public final class StreamChannel extends AbstractAggregateRoot<StreamChannel> {
    private final UUID id;
    private final StreamChannelContentAccountId streamChannelContentAccountId;
    private final StreamChannelPlatform platform;
    private final List<Stream> streams;
    private StreamChannelStatus status;

    public StreamChannel(StreamChannelContentAccountId streamChannelContentAccountId, StreamChannelPlatform platform) {
        this.id = UUID.randomUUID();
        this.streamChannelContentAccountId = streamChannelContentAccountId;
        this.platform = platform;
        this.streams = new ArrayList<>();
        this.status = StreamChannelStatus.FROZEN;
    }

    public void startObserving() {
        if (status.equals(StreamChannelStatus.OBSERVABLE)) {
            throw new StreamChannelIsAlreadyObservableException();
        }
        this.status = StreamChannelStatus.OBSERVABLE;
    }

    public void freeze() {
        if (status.equals(StreamChannelStatus.FROZEN)) {
            throw new StreamChannelIsAlreadyFrozenException();
        }
        this.status = StreamChannelStatus.FROZEN;
    }

    public void addStreamData(StreamData data) {
        Optional<Stream> liveStream = streams.stream().filter(Stream::isLive).findAny();
        if (liveStream.isEmpty()) {
            createNewStream(data);
            return;
        }

        if (liveStream.get().addStreamData(data)) {
            registerEvent(new NewContentAccountDataEvent<>(new ContentAccountData(data.toString())));
        }
    }

    public void endStream(LocalDateTime time) {
        Optional<Stream> liveStream = streams.stream().filter(Stream::isLive).findAny();
        if (liveStream.isEmpty()) {
            throw new NoLiveStreamFoundException();
        }

        liveStream.get().endStream(time);
        registerEvent(new StreamChannelGoneOfflineEvent(this.getStreamChannelContentAccountId().getId()));
    }

    private void createNewStream(StreamData data) {
        Stream newStream = new Stream(data);
        streams.add(newStream);
        registerEvent(new StreamChannelIsLiveEvent(this.getStreamChannelContentAccountId().getId()));
        registerEvent(new NewContentAccountDataEvent<>(new ContentAccountData(data.toString())));
    }

    public boolean isLive() {
        return streams.stream().anyMatch(Stream::isLive);
    }

    public Collection<Object> getEvents() {
        return domainEvents();
    }
}
