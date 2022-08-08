package com.github.he305.contentcore.watchinglist.application.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;

@Getter
public class AddWatchingEntryDto {
    private WatchingListEntryDto entry;
}
