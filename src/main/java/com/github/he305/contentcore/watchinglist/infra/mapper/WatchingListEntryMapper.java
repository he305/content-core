package com.github.he305.contentcore.watchinglist.infra.mapper;

import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListData;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListEntryData;

public interface WatchingListEntryMapper {
    WatchingListEntryData toJpa(WatchingListEntry entry, WatchingListData watchingListData);

    WatchingListEntry toDomain(WatchingListEntryData data);
}
