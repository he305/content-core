package com.github.he305.contentcore.stream.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class StreamChannelGoneOfflineEvent {
    private final UUID contentAccountId;
}
