package com.github.he305.contentcore.watchinglist.domain.repository;

import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;

import java.util.Optional;

public interface WatchingListRepository {
    void save(WatchingList watchingList);

    Optional<WatchingList> getWatchingListByMemberId(MemberId memberId);
}
