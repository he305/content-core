package com.github.he305.contentcore.watchinglist.domain.model.entities;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WatchingListEntryTest {

    @Test
    void testEquals_equals() {
        UUID id = UUID.randomUUID();
        UUID contentEntryId = UUID.randomUUID();
        UUID contentAccountId = UUID.randomUUID();
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry(contentEntryId, new ContentAccountId(contentAccountId), Set.of());
        WatchingListEntry first = new WatchingListEntry(id, new ContentCreator("name"), Set.of(contentAccountEntry));
        WatchingListEntry second = new WatchingListEntry(id, new ContentCreator("name"), Set.of(contentAccountEntry));
        assertEquals(first, second);
    }

    @Test
    void testEquals_notEquals() {
        UUID id = UUID.randomUUID();
        WatchingListEntry first = new WatchingListEntry(new ContentCreator("name"), Set.of(new ContentAccountId(id)));
        WatchingListEntry second = new WatchingListEntry(new ContentCreator("name"), Set.of(new ContentAccountId(id)));
        assertNotEquals(first, second);
    }

    @Test
    void testHashCode_equals() {
        UUID id = UUID.randomUUID();
        UUID contentEntryId = UUID.randomUUID();
        UUID contentAccountId = UUID.randomUUID();
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry(contentEntryId, new ContentAccountId(contentAccountId), Set.of());
        WatchingListEntry first = new WatchingListEntry(id, new ContentCreator("name"), Set.of(contentAccountEntry));
        WatchingListEntry second = new WatchingListEntry(id, new ContentCreator("name"), Set.of(contentAccountEntry));
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void addContentAccount() {
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"));
        ContentAccountId id = new ContentAccountId(UUID.randomUUID());

        assertTrue(entry.addContentAccount(id));
        assertFalse(entry.addContentAccount(id));
    }

    @Test
    void removeContentAccount() {
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"));
        ContentAccountId id = new ContentAccountId(UUID.randomUUID());

        assertFalse(entry.removeContentAccount(id));
        assertTrue(entry.addContentAccount(id));
        assertTrue(entry.removeContentAccount(id));
        assertFalse(entry.removeContentAccount(id));
    }

    @Test
    void addNotificationForContentAccountId() {
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"));
        ContentAccountId id = new ContentAccountId(UUID.randomUUID());
        UUID notificationId = UUID.randomUUID();

        entry.addNotificationForContentAccountId(id, notificationId);
        assertTrue(entry.getContentAccountSet().isEmpty());
        entry.addContentAccount(id);
        assertEquals(1, entry.getContentAccountIdSet().size());
        entry.addNotificationForContentAccountId(id, notificationId);
        assertEquals(1, entry.getContentAccountIdSet().size());
    }

    @Test
    void getAndDeleteNotificationsForContentAccountId() {
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"));
        ContentAccountId id = new ContentAccountId(UUID.randomUUID());
        UUID notificationId = UUID.randomUUID();

        Set<NotificationId> empty = entry.getAndDeleteNotificationsForContentAccountId(id);
        assertTrue(empty.isEmpty());

        entry.addNotificationForContentAccountId(id, notificationId);
        assertTrue(entry.getContentAccountSet().isEmpty());
        entry.addContentAccount(id);
        assertEquals(1, entry.getContentAccountIdSet().size());
        entry.addNotificationForContentAccountId(id, notificationId);
        assertEquals(1, entry.getContentAccountIdSet().size());

        Set<NotificationId> actual = entry.getAndDeleteNotificationsForContentAccountId(id);
        assertEquals(1, actual.size());
        assertTrue(actual.contains(new NotificationId(notificationId)));

        Set<NotificationId> actual2 = entry.getAndDeleteNotificationsForContentAccountId(id);
        assertEquals(0, actual2.size());
    }
}