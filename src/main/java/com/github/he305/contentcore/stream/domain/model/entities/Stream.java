package com.github.he305.contentcore.stream.domain.model.entities;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "id")
public final class Stream {
    @NonNull
    private final UUID id;
    @NonNull
    private final LocalDateTime startedAt;
    @NonNull
    private final List<StreamData> streamDataList;
    private boolean isLive;
    private int maxViewers;
    private LocalDateTime endedAt;

    public Stream(StreamData streamData) {
        this.id = UUID.randomUUID();
        this.startedAt = streamData.getStreamDataTime();
        this.streamDataList = new ArrayList<>();
        this.isLive = true;
        addStreamData(streamData);
    }

    public Stream(UUID id, boolean isLive, int maxViewers, LocalDateTime startedAt, LocalDateTime endedAt, List<StreamData> streamDataList) {
        this.id = id;
        this.isLive = isLive;
        this.maxViewers = maxViewers;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.streamDataList = streamDataList;
    }

    public boolean addStreamData(StreamData streamData) {
        if (maxViewers < streamData.getViewerCount()) {
            maxViewers = streamData.getViewerCount();
        }

        boolean isNewData;
        if (!streamDataList.isEmpty()) {
            isNewData = hasNewData(streamData);
        } else {
            isNewData = true;
        }

        streamDataList.add(streamData);
        return isNewData;
    }

    private boolean hasNewData(StreamData streamData) {
        StreamData last = streamDataList.get(streamDataList.size() - 1);
        return !last.getGameName().equals(streamData.getGameName()) ||
                !(last.getTitle()).equals(streamData.getTitle());
    }

    public void endStream(LocalDateTime time) {
        this.endedAt = time;
        this.isLive = false;
    }

    public StreamData getLastData() {
        return streamDataList.stream().max(Comparator.comparing(StreamData::getStreamDataTime))
                .orElseThrow(() -> new ContentCoreException("Error, stream exists without stream data"));
    }

    public boolean isLive() {
        return isLive;
    }
}
