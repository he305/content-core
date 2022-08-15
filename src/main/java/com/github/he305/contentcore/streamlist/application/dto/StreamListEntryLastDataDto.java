package com.github.he305.contentcore.streamlist.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class StreamListEntryLastDataDto {
    private final String title;
    private final String gameName;
    private final int viewerCount;

    public static StreamListEntryLastDataDto empty() {
        return new StreamListEntryLastDataDto(
                "",
                "",
                0
        );
    }
}
