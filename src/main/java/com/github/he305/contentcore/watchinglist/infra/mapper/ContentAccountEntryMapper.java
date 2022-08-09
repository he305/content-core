package com.github.he305.contentcore.watchinglist.infra.mapper;

import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.infra.data.ContentAccountEntryData;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListEntryData;

public interface ContentAccountEntryMapper {
    ContentAccountEntryData toJpa(ContentAccountEntry entry, WatchingListEntryData watchingListEntryData);

    ContentAccountEntry toDomain(ContentAccountEntryData jpa);
}
