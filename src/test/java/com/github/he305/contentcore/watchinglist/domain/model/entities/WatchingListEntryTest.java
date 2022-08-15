package com.github.he305.contentcore.watchinglist.domain.model.entities;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WatchingListEntryTest {

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(WatchingListEntry.class).verify();
    }

    @Test
    void testEquals_equals() {
        UUID id = UUID.randomUUID();
        UUID contentEntryId = UUID.randomUUID();
        UUID contentAccountId = UUID.randomUUID();
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry(contentEntryId, "some", new ContentAccountId(contentAccountId), Set.of());
        WatchingListEntry first = new WatchingListEntry(id, new ContentCreator("name"), Set.of(contentAccountEntry));
        WatchingListEntry second = new WatchingListEntry(id, new ContentCreator("name"), Set.of(contentAccountEntry));
        assertEquals(first, second);
    }

    @Test
    void testEquals_notEquals() {
        UUID id = UUID.randomUUID();
        ContentAccountId contentAccountId = new ContentAccountId(id);
        WatchingListEntry first = new WatchingListEntry(new ContentCreator("name"), Set.of(new ContentAccountEntry("name", contentAccountId)));
        WatchingListEntry second = new WatchingListEntry(new ContentCreator("name"), Set.of(new ContentAccountEntry("name", contentAccountId)));
        assertNotEquals(first, second);
    }

    @Test
    void testHashCode_equals() {
        UUID id = UUID.randomUUID();
        UUID contentEntryId = UUID.randomUUID();
        UUID contentAccountId = UUID.randomUUID();
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry(contentEntryId, "test", new ContentAccountId(contentAccountId), Set.of());
        WatchingListEntry first = new WatchingListEntry(id, new ContentCreator("name"), Set.of(contentAccountEntry));
        WatchingListEntry second = new WatchingListEntry(id, new ContentCreator("name"), Set.of(contentAccountEntry));
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void addContentAccount() {
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"));
        ContentAccountId contentAccountId = new ContentAccountId(UUID.randomUUID());
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry("test", contentAccountId);

        assertTrue(entry.addContentAccount(contentAccountEntry));
        assertFalse(entry.addContentAccount(contentAccountEntry));

        ContentAccountEntry sameIdAnotherAlias = new ContentAccountEntry("another alias", contentAccountId);
        assertFalse(entry.addContentAccount(sameIdAnotherAlias));
    }

    @Test
    void removeContentAccount() {
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"));
        ContentAccountId contentAccountId = new ContentAccountId(UUID.randomUUID());
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry("test", contentAccountId);

        assertFalse(entry.removeContentAccount(contentAccountEntry));
        assertTrue(entry.addContentAccount(contentAccountEntry));
        assertTrue(entry.removeContentAccount(contentAccountEntry));
        assertFalse(entry.removeContentAccount(contentAccountEntry));

        assertTrue(entry.addContentAccount(contentAccountEntry));
        ContentAccountEntry sameIdAnotherAlias = new ContentAccountEntry("another alias", contentAccountId);
        assertTrue(entry.removeContentAccount(sameIdAnotherAlias));
    }

    @Test
    void addNotificationForContentAccountId() {
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"));
        ContentAccountId contentAccountId = new ContentAccountId(UUID.randomUUID());
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry("test", contentAccountId);
        UUID notificationId = UUID.randomUUID();

        entry.addNotificationForContentAccountId(contentAccountId, notificationId);
        assertTrue(entry.getContentAccountSet().isEmpty());
        entry.addContentAccount(contentAccountEntry);
        assertEquals(1, entry.getContentAccountIdSet().size());
        entry.addNotificationForContentAccountId(contentAccountId, notificationId);
        assertEquals(1, entry.getContentAccountIdSet().size());
    }

    @Test
    void getAndDeleteNotificationsForContentAccountId() {
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"));
        ContentAccountId contentAccountId = new ContentAccountId(UUID.randomUUID());
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry("test", contentAccountId);
        UUID notificationId = UUID.randomUUID();

        Set<NotificationId> empty = entry.getAndDeleteNotificationsForContentAccountId(contentAccountId);
        assertTrue(empty.isEmpty());

        entry.addNotificationForContentAccountId(contentAccountId, notificationId);
        assertTrue(entry.getContentAccountSet().isEmpty());
        entry.addContentAccount(contentAccountEntry);
        assertEquals(1, entry.getContentAccountIdSet().size());
        entry.addNotificationForContentAccountId(contentAccountId, notificationId);
        assertEquals(1, entry.getContentAccountIdSet().size());

        Set<NotificationId> actual = entry.getAndDeleteNotificationsForContentAccountId(contentAccountId);
        assertEquals(1, actual.size());
        assertTrue(actual.contains(new NotificationId(notificationId)));

        Set<NotificationId> actual2 = entry.getAndDeleteNotificationsForContentAccountId(contentAccountId);
        assertEquals(0, actual2.size());
    }
}