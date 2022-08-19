package com.github.he305.contentcore.watchinglist.domain.model.entities;

import com.github.he305.contentcore.watchinglist.domain.model.enums.WatchingListEntryUpdateResult;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import com.github.he305.contentcore.watchinglist.domain.model.values.WatchingListContentAccountId;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Map;
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
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry(contentEntryId, "some", new WatchingListContentAccountId(contentAccountId), Set.of());
        WatchingListEntry first = new WatchingListEntry(id, new ContentCreator("name"), Set.of(contentAccountEntry));
        WatchingListEntry second = new WatchingListEntry(id, new ContentCreator("name"), Set.of(contentAccountEntry));
        assertEquals(first, second);
    }

    @Test
    void testEquals_notEquals() {
        UUID id = UUID.randomUUID();
        WatchingListContentAccountId watchingListContentAccountId = new WatchingListContentAccountId(id);
        WatchingListEntry first = new WatchingListEntry(new ContentCreator("name"), Set.of(new ContentAccountEntry("name", watchingListContentAccountId)));
        WatchingListEntry second = new WatchingListEntry(new ContentCreator("name"), Set.of(new ContentAccountEntry("name", watchingListContentAccountId)));
        assertNotEquals(first, second);
    }

    @Test
    void testHashCode_equals() {
        UUID id = UUID.randomUUID();
        UUID contentEntryId = UUID.randomUUID();
        UUID contentAccountId = UUID.randomUUID();
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry(contentEntryId, "test", new WatchingListContentAccountId(contentAccountId), Set.of());
        WatchingListEntry first = new WatchingListEntry(id, new ContentCreator("name"), Set.of(contentAccountEntry));
        WatchingListEntry second = new WatchingListEntry(id, new ContentCreator("name"), Set.of(contentAccountEntry));
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void addContentAccount() {
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"));
        WatchingListContentAccountId watchingListContentAccountId = new WatchingListContentAccountId(UUID.randomUUID());
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry("test", watchingListContentAccountId);

        assertTrue(entry.addContentAccount(contentAccountEntry));
        assertFalse(entry.addContentAccount(contentAccountEntry));

        ContentAccountEntry sameIdAnotherAlias = new ContentAccountEntry("another alias", watchingListContentAccountId);
        assertFalse(entry.addContentAccount(sameIdAnotherAlias));
    }

    @Test
    void removeContentAccount() {
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"));
        WatchingListContentAccountId watchingListContentAccountId = new WatchingListContentAccountId(UUID.randomUUID());
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry("test", watchingListContentAccountId);

        assertFalse(entry.removeContentAccount(contentAccountEntry));
        assertTrue(entry.addContentAccount(contentAccountEntry));
        assertTrue(entry.removeContentAccount(contentAccountEntry));
        assertFalse(entry.removeContentAccount(contentAccountEntry));

        assertTrue(entry.addContentAccount(contentAccountEntry));
        ContentAccountEntry sameIdAnotherAlias = new ContentAccountEntry("another alias", watchingListContentAccountId);
        assertTrue(entry.removeContentAccount(sameIdAnotherAlias));
    }

    @Test
    void addNotificationForContentAccountId() {
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"));
        WatchingListContentAccountId watchingListContentAccountId = new WatchingListContentAccountId(UUID.randomUUID());
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry("test", watchingListContentAccountId);
        UUID notificationId = UUID.randomUUID();

        entry.addNotificationForContentAccountId(watchingListContentAccountId, notificationId);
        assertTrue(entry.getContentAccountSet().isEmpty());
        entry.addContentAccount(contentAccountEntry);
        assertEquals(1, entry.getContentAccountIdSet().size());
        entry.addNotificationForContentAccountId(watchingListContentAccountId, notificationId);
        assertEquals(1, entry.getContentAccountIdSet().size());
    }

    @Test
    void getAndDeleteNotificationsForContentAccountId() {
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"));
        WatchingListContentAccountId watchingListContentAccountId = new WatchingListContentAccountId(UUID.randomUUID());
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry("test", watchingListContentAccountId);
        UUID notificationId = UUID.randomUUID();

        Set<NotificationId> empty = entry.getAndDeleteNotificationsForContentAccountId(watchingListContentAccountId);
        assertTrue(empty.isEmpty());

        entry.addNotificationForContentAccountId(watchingListContentAccountId, notificationId);
        assertTrue(entry.getContentAccountSet().isEmpty());
        entry.addContentAccount(contentAccountEntry);
        assertEquals(1, entry.getContentAccountIdSet().size());
        entry.addNotificationForContentAccountId(watchingListContentAccountId, notificationId);
        assertEquals(1, entry.getContentAccountIdSet().size());

        Set<NotificationId> actual = entry.getAndDeleteNotificationsForContentAccountId(watchingListContentAccountId);
        assertEquals(1, actual.size());
        assertTrue(actual.contains(new NotificationId(notificationId)));

        Set<NotificationId> actual2 = entry.getAndDeleteNotificationsForContentAccountId(watchingListContentAccountId);
        assertEquals(0, actual2.size());
    }

    @Test
    void updateEntry_addNew_removeExisting_tryToAddAgain_renameExisting() {
        WatchingListContentAccountId id = new WatchingListContentAccountId(UUID.randomUUID());
        ContentAccountEntry toRemove = new ContentAccountEntry("test", id);
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"), Set.of(
                toRemove
        ));

        WatchingListContentAccountId newId = new WatchingListContentAccountId(UUID.randomUUID());
        ContentAccountEntry toAdd = new ContentAccountEntry("test", newId);
        Set<ContentAccountEntry> toAddSet = Set.of(
                toAdd
        );

        Map<ContentAccountEntry, WatchingListEntryUpdateResult> resultMap = entry.updateEntry(toAddSet);
        assertEquals(2, resultMap.size());
        assertEquals(WatchingListEntryUpdateResult.REMOVED, resultMap.get(toRemove));
        assertEquals(WatchingListEntryUpdateResult.ADDED, resultMap.get(toAdd));

        Map<ContentAccountEntry, WatchingListEntryUpdateResult> addAgainResultMap = entry.updateEntry(toAddSet);
        assertEquals(1, addAgainResultMap.size());
        assertEquals(WatchingListEntryUpdateResult.UNCHANGED, addAgainResultMap.get(toAdd));

        ContentAccountEntry toRename = new ContentAccountEntry("new name", newId);
        Set<ContentAccountEntry> toRenameSet = Set.of(
                toRename
        );

        Map<ContentAccountEntry, WatchingListEntryUpdateResult> renameResultMap = entry.updateEntry(toRenameSet);
        assertEquals(1, renameResultMap.size());
        assertEquals(WatchingListEntryUpdateResult.NAME_CHANGED, renameResultMap.get(toRename));
    }
}