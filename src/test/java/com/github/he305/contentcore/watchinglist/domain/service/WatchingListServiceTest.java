package com.github.he305.contentcore.watchinglist.domain.service;

import com.github.he305.contentcore.notification.domain.events.NewNotificationEvent;
import com.github.he305.contentcore.shared.events.EventPublisher;
import com.github.he305.contentcore.streamlist.domain.events.RequestUpdateStreamListEvent;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import com.github.he305.contentcore.watchinglist.domain.model.values.WatchingListContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WatchingListServiceTest {

    @Mock
    private WatchingListRepository watchingListRepository;
    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private WatchingListService underTest;

    @Test
    void onNewNotificationEvent() {
        UUID contentCreatorId = UUID.randomUUID();
        WatchingListEntry entry = new WatchingListEntry(
                new ContentCreator("name"),
                Set.of(new ContentAccountEntry("test", new WatchingListContentAccountId(contentCreatorId)))
        );
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID(),
                List.of(entry));
        assertEquals(1, watchingList.getWatchingListEntries().size());

        Mockito.when(watchingListRepository.getAll()).thenReturn(List.of(watchingList));
        UUID notificationId = UUID.randomUUID();
        NewNotificationEvent event = new NewNotificationEvent(notificationId, contentCreatorId);
        assertDoesNotThrow(() -> underTest.onNewNotificationEvent(event));

        assertEquals(1, watchingList.getWatchingListEntries().size());
    }

    @Test
    void onRequestUpdateStreamListEvent_listNotFound() {
        UUID id = UUID.randomUUID();
        RequestUpdateStreamListEvent event = new RequestUpdateStreamListEvent(id);

        Mockito.when(watchingListRepository.getWatchingListByMemberId(new MemberId(id))).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> underTest.onRequestUpdateStreamListEvent(event));
    }

    @Test
    void onRequestUpdateStreamListEvent_valid() {
        UUID id = UUID.randomUUID();
        RequestUpdateStreamListEvent event = new RequestUpdateStreamListEvent(id);

        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());
        watchingList.addWatchingListEntry("name", Set.of(new ContentAccountEntry("test", new WatchingListContentAccountId(UUID.randomUUID()))));
        Mockito.when(watchingListRepository.getWatchingListByMemberId(new MemberId(id))).thenReturn(Optional.of(watchingList));
        assertDoesNotThrow(() -> underTest.onRequestUpdateStreamListEvent(event));
    }
}