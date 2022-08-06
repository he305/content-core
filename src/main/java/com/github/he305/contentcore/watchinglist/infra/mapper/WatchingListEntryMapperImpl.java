package com.github.he305.contentcore.watchinglist.infra.mapper;

import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.infra.data.ContentAccountIdData;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListData;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListEntryData;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WatchingListEntryMapperImpl implements WatchingListEntryMapper {
    @Override
    public WatchingListEntryData toJpa(WatchingListEntry entry, WatchingListData watchingListData) {
        Set<ContentAccountIdData> contentAccountIdDataSet = entry
                .getContentAccountSet()
                .stream()
                .map(id -> new ContentAccountIdData(id.getId()))
                .collect(Collectors.toSet());
        return new WatchingListEntryData(entry.getId(), entry.getContentCreatorName(), contentAccountIdDataSet, watchingListData);
    }

    @Override
    public WatchingListEntry toDomain(WatchingListEntryData data) {
        Set<ContentAccountId> contentAccountIds = data
                .getContentAccountIdDataSet()
                .stream()
                .map(id -> new ContentAccountId(id.getId()))
                .collect(Collectors.toSet());

        return new WatchingListEntry(data.getId(), new ContentCreator(data.getName()), contentAccountIds);
    }
}
