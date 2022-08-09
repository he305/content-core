package com.github.he305.contentcore.watchinglist.domain.service;

import com.github.he305.contentcore.notification.domain.events.NewNotificationEvent;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchingListService {
    private final WatchingListRepository watchingListRepository;

    @EventListener
    public void onNewNotificationEvent(NewNotificationEvent event) {
        List<WatchingList> watchingListList = watchingListRepository.getAll();
        watchingListList.forEach(watchingList -> {
            watchingList.addNotificationForContentAccount(event.getContentAccountId(), event.getNotificationId());
            watchingListRepository.save(watchingList);
        });
    }
}
