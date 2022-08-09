package com.github.he305.contentcore.watchinglist.domain.service;

import com.github.he305.contentcore.notification.domain.events.NewNotificationEvent;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WatchingListServiceTest {

    @Mock
    private WatchingListRepository watchingListRepository;

    @InjectMocks
    private WatchingListService underTest;

    @Test
    void onNewNotificationEvent() {
        UUID contentCreatorId = UUID.randomUUID();
        WatchingListEntry entry = new WatchingListEntry(
                new ContentCreator("name"),
                Set.of(new ContentAccountId(contentCreatorId))
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
}