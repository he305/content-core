package com.github.he305.contentcore.watchinglist.infra.mapper;

import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListData;

public interface WatchingListMapper {
    WatchingListData toJpa(WatchingList watchingList);

    WatchingList toDomain(WatchingListData data);
}
