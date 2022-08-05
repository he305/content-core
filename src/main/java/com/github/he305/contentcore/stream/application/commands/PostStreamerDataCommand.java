package com.github.he305.contentcore.stream.application.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class PostStreamerDataCommand {
    private final UUID streamChannelId;
    private final String name;
    private final String title;
    private final int viewerCount;
    private final LocalDateTime time;
}
