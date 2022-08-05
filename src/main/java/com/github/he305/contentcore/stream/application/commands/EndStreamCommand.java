package com.github.he305.contentcore.stream.application.commands;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class EndStreamCommand {
    private final UUID streamChannelId;
    private final LocalDateTime time;
}
