package com.github.he305.contentcore.streamlist.application.dto;

import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class StreamListEntryDto {
    private final String name;
    private final StreamChannelPlatform platform;
    private final boolean isLive;
    private final String url;
    private final StreamListEntryLastDataDto currentData;
}
