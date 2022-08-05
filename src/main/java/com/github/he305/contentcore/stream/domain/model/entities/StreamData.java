package com.github.he305.contentcore.stream.domain.model.entities;

import com.github.he305.contentcore.shared.entities.BaseEntity;
import com.github.he305.contentcore.shared.validators.PositiveOrZeroNumberValidator;
import com.github.he305.contentcore.shared.validators.StringValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class StreamData extends BaseEntity {
    private final String gameName;
    private final String title;
    private final int viewerCount;
    private final LocalDateTime streamDataTime;

    public StreamData(String gameName, String title, int viewerCount, LocalDateTime streamDataTime) {
        this.gameName = StringValidator.isNullOrEmpty(gameName);
        this.title = StringValidator.isNullOrEmpty(title);
        this.viewerCount = PositiveOrZeroNumberValidator.validate(viewerCount);
        // TODO: somehow validate time?
        this.streamDataTime = streamDataTime;
    }

    public StreamData(UUID id, String gameName, String title, int viewerCount, LocalDateTime streamDataTime) {
        this.id = id;
        this.gameName = StringValidator.isNullOrEmpty(gameName);
        this.title = StringValidator.isNullOrEmpty(title);
        this.viewerCount = PositiveOrZeroNumberValidator.validate(viewerCount);
        // TODO: somehow validate time?
        this.streamDataTime = streamDataTime;
    }
}