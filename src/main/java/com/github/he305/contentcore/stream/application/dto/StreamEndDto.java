package com.github.he305.contentcore.stream.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class StreamEndDto {
    private final UUID streamerChannelId;
    private final LocalDateTime endTime;
}
