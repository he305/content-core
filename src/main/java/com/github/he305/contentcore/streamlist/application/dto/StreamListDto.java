package com.github.he305.contentcore.streamlist.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class StreamListDto {
    private final List<StreamListEntryDto> entries;
}
