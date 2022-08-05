package com.github.he305.contentcore.watchinglist.infra.repository;

import com.github.he305.contentcore.shared.events.EventPublisher;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import com.github.he305.contentcore.watchinglist.infra.data.WatchingListData;
import com.github.he305.contentcore.watchinglist.infra.jpa.JpaWatchingListRepository;
import com.github.he305.contentcore.watchinglist.infra.mapper.WatchingListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WatchingListRepositoryImpl implements WatchingListRepository {
    private final JpaWatchingListRepository jpaWatchingListRepository;
    private final WatchingListMapper watchingListMapper;
    private final EventPublisher eventPublisher;

    @Override
    public void save(WatchingList watchingList) {
        WatchingListData watchingListData = watchingListMapper.toJpa(watchingList);
        jpaWatchingListRepository.save(watchingListData);
        eventPublisher.publishEvent(watchingList.getEvents());
    }

    @Override
    public Optional<WatchingList> getWatchingListByMemberId(MemberId memberId) {
        Optional<WatchingListData> optionalWatchingListData = jpaWatchingListRepository.getByMemberId(memberId.getId());
        if (optionalWatchingListData.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(watchingListMapper.toDomain(optionalWatchingListData.get()));
    }
}
