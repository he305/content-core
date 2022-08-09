package com.github.he305.contentcore.watchinglist.infra.mapper;

import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.infra.data.ContentAccountEntryData;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListData;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListEntryData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WatchingListEntryMapperImpl implements WatchingListEntryMapper {
    private final ContentAccountEntryMapper contentAccountEntryMapper;

    @Override
    public WatchingListEntryData toJpa(WatchingListEntry entry, WatchingListData watchingListData) {
        WatchingListEntryData entryData = WatchingListEntryData
                .builder()
                .id(entry.getId())
                .name(entry.getContentCreatorName())
                .watchingList(watchingListData)
                .build();

        List<ContentAccountEntryData> contentAccountEntryDataSet = entry
                .getContentAccountSet()
                .stream()
                .map(contentAccountEntry -> contentAccountEntryMapper.toJpa(contentAccountEntry, entryData))
                .collect(Collectors.toList());
        entryData.setContentAccountEntryDataList(contentAccountEntryDataSet);
        return new WatchingListEntryData(entry.getId(), entry.getContentCreatorName(), contentAccountEntryDataSet, watchingListData);
    }

    @Override
    public WatchingListEntry toDomain(WatchingListEntryData data) {
        Set<ContentAccountEntry> contentAccountEntries = data
                .getContentAccountEntryDataSet()
                .stream()
                .map(contentAccountEntryMapper::toDomain)
                .collect(Collectors.toSet());

        return new WatchingListEntry(data.getId(), new ContentCreator(data.getName()), contentAccountEntries);
    }
}
