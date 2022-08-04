package com.github.he305.contentcore.watchinglist.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class WatchingListEntryDto {
    private String name;
    private List<ContentAccountDto> accounts;
}
