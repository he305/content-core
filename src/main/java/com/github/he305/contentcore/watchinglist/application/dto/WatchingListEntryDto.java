package com.github.he305.contentcore.watchinglist.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchingListEntryDto {
    private String name;
    private List<ContentAccountDto> accounts;
}
