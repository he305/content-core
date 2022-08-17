package com.github.he305.contentcore.stream.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StreamEndDto {
    private UUID streamerChannelId;
    private LocalDateTime endTime;
}
