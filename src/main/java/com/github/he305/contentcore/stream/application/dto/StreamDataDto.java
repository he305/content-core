package com.github.he305.contentcore.stream.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class StreamDataDto {
    private final UUID streamChannelId;
    private final String name;
    private final String title;
    private final int viewerCount;
    private final LocalDateTime time;
}
