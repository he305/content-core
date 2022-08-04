package com.github.he305.contentcore.watchinglist.infra.mapper;

import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListData;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListEntryData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WatchingListMapperImpl implements WatchingListMapper {
    private final WatchingListEntryMapper watchingListEntryMapper;

    @Override
    public WatchingListData toJpa(WatchingList watchingList) {
        WatchingListData data = WatchingListData
                .builder()
                .id(watchingList.getId())
                .memberId(watchingList.getMemberId().getId())
                .build();

        List<WatchingListEntryData> watchingListEntryDataList = watchingList
                .getWatchingListEntries()
                .stream()
                .map(entry -> watchingListEntryMapper.toJpa(entry, data))
                .collect(Collectors.toList());


        data.setWatchingListEntryDataList(watchingListEntryDataList);
        return data;
    }

    @Override
    public WatchingList toDomain(WatchingListData data) {
        List<WatchingListEntry> watchingListEntries = data
                .getWatchingListEntryDataList()
                .stream()
                .map(watchingListEntryMapper::toDomain)
                .collect(Collectors.toList());

        return new WatchingList(data.getId(), data.getMemberId(), watchingListEntries);
    }
}
