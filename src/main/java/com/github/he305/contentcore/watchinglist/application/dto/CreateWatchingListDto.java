package com.github.he305.contentcore.watchinglist.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CreateWatchingListDto {
    private List<WatchingListEntryDto> watchingList;
}
