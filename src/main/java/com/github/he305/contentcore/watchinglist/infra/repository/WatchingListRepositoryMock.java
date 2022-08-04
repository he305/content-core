package com.github.he305.contentcore.watchinglist.infra.repository;

import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @deprecated Will be deleted soon
 */
@Deprecated(since = "0.1.0", forRemoval = true)
public class WatchingListRepositoryMock implements WatchingListRepository {
    List<WatchingList> watchingLists = new ArrayList<>();

    @Override
    public void save(WatchingList watchingList) {
        watchingLists.stream()
                .filter(existing -> watchingList.getMemberId().equals(existing.getMemberId()))
                .findAny()
                .ifPresent(list -> watchingLists.remove(list));
        watchingLists.add(watchingList);
    }

    @Override
    public Optional<WatchingList> getWatchingListByMemberId(MemberId memberId) {
        return watchingLists.stream().filter(watchingList -> watchingList.getMemberId().equals(memberId)).findAny();
    }
}
