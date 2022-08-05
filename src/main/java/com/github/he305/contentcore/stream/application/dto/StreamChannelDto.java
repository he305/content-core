package com.github.he305.contentcore.stream.application.dto;

import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class StreamChannelDto {
    private UUID id;
    private String channelName;
    private StreamChannelPlatform platform;
    private boolean isLive;
}
