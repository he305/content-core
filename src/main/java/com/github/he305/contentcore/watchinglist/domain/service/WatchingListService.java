package com.github.he305.contentcore.watchinglist.domain.service;

import com.github.he305.contentcore.notification.domain.events.NewNotificationEvent;
import com.github.he305.contentcore.shared.events.EventPublisher;
import com.github.he305.contentcore.streamlist.domain.events.RequestUpdateStreamListEvent;
import com.github.he305.contentcore.watchinglist.domain.events.WatchingListContentAccountAddedEvent;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WatchingListService {
    private final WatchingListRepository watchingListRepository;
    private final EventPublisher eventPublisher;

    @EventListener
    public void onNewNotificationEvent(NewNotificationEvent event) {
        List<WatchingList> watchingListList = watchingListRepository.getAll();
        watchingListList.forEach(watchingList -> {
            watchingList.addNotificationForContentAccount(event.getContentAccountId(), event.getNotificationId());
            watchingListRepository.save(watchingList);
        });
    }

    @EventListener
    public void onRequestUpdateStreamListEvent(RequestUpdateStreamListEvent event) {
        Optional<WatchingList> watchingList = watchingListRepository.getWatchingListByMemberId(new MemberId(event.getMemberId()));
        if (watchingList.isEmpty()) {
            return;
        }

        Collection<Object> eventsToPublish = new ArrayList<>();
        watchingList.get().getWatchingListEntries().forEach(entry ->
                entry.getContentAccountIdSet().forEach(contentAccountId -> eventsToPublish.add(new WatchingListContentAccountAddedEvent(contentAccountId.getId(), event.getMemberId())))
        );
        eventPublisher.publishEvent(eventsToPublish);
    }
}
