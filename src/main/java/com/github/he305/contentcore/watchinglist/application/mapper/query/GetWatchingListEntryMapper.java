package com.github.he305.contentcore.watchinglist.application.mapper.query;

import com.github.he305.contentcore.watchinglist.application.dto.query.GetWatchingListEntryDto;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;

public interface GetWatchingListEntryMapper {
    GetWatchingListEntryDto toDto(WatchingListEntry entry);
}
