package com.github.he305.contentcore.watchinglist.domain.model;

import com.github.he305.contentcore.watchinglist.application.exceptions.ContentAccountSetEmptyException;
import com.github.he305.contentcore.watchinglist.application.exceptions.WatchingListEntryAlreadyExistException;
import com.github.he305.contentcore.watchinglist.application.exceptions.WatchingListEntryNotExistsException;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import com.github.he305.contentcore.watchinglist.domain.model.values.WatchingListContentAccountId;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WatchingListTest {

    @Test
    void updateWatchingListEntry_entityNotExists_shouldThrow() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", new WatchingListContentAccountId(UUID.randomUUID())));

        assertThrows(WatchingListEntryNotExistsException.class, () ->
                watchingList.updateWatchingListEntry(name, set));
    }

    @Test
    void updateWatchingListEntry_emptySet_shouldThrow() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of();

        assertThrows(ContentAccountSetEmptyException.class, () ->
                watchingList.updateWatchingListEntry(name, set));
    }

    @Test
    void updateWatchingListEntry_valid() {
        WatchingListContentAccountId existing = new WatchingListContentAccountId(UUID.randomUUID());
        WatchingListContentAccountId intersectsChanged = new WatchingListContentAccountId(UUID.randomUUID());
        WatchingListContentAccountId intersectsUnchanged = new WatchingListContentAccountId(UUID.randomUUID());
        WatchingListContentAccountId newId = new WatchingListContentAccountId(UUID.randomUUID());


        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        ContentAccountEntry existingAccount = new ContentAccountEntry("test0", existing);
        ContentAccountEntry intersectsChangedAccount = new ContentAccountEntry("test1", intersectsChanged);
        ContentAccountEntry unchangedAccount = new ContentAccountEntry("unchanged", intersectsUnchanged);
        String name = "testName";
        Set<ContentAccountEntry> oldSet = Set.of(
                existingAccount,
                intersectsChangedAccount,
                unchangedAccount
        );

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, oldSet));
        int initialEventSize = watchingList.getEvents().size();

        ContentAccountEntry addedAccount = new ContentAccountEntry("test2", newId);
        Set<ContentAccountEntry> newSet = Set.of(
                unchangedAccount,
                intersectsChangedAccount,
                addedAccount
        );

        assertDoesNotThrow(() -> watchingList.updateWatchingListEntry(name, newSet));
        List<WatchingListEntry> entryList = watchingList.getWatchingListEntries();
        assertEquals(1, entryList.size());

        Set<ContentAccountEntry> resultSet = watchingList.getWatchingListEntries().get(0).getContentAccountSet();
        assertEquals(newSet.size(), resultSet.size());

        assertEquals(name, watchingList.getWatchingListEntries().get(0).getContentCreatorName());
        Collection<Object> events = watchingList.getEvents();
        assertEquals(4, events.size() - initialEventSize);
    }

    @Test
    void addWatchingListEntry_emptySet_shouldThrow() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of();

        assertThrows(ContentAccountSetEmptyException.class, () ->
                watchingList.addWatchingListEntry(name, set));
    }

    @Test
    void addWatchingListEntry_entryAlreadyExists_shouldThrow() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", new WatchingListContentAccountId(UUID.randomUUID())));

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, set));
        assertThrows(WatchingListEntryAlreadyExistException.class, () ->
                watchingList.addWatchingListEntry(name, set));
    }

    @Test
    void addWatchingListEntry_valid() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", new WatchingListContentAccountId(UUID.randomUUID())));

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, set));
        List<WatchingListEntry> entryList = watchingList.getWatchingListEntries();
        assertEquals(1, entryList.size());

        Set<ContentAccountEntry> newSet = watchingList.getWatchingListEntries().get(0).getContentAccountSet();
        assertEquals(set, newSet);
        assertEquals(name, watchingList.getWatchingListEntries().get(0).getContentCreatorName());
    }

    @Test
    void testJpaConstructor() {
        WatchingListEntry entry = new WatchingListEntry(new ContentCreator("name"));
        WatchingList list = new WatchingList(UUID.randomUUID(), UUID.randomUUID(), List.of(entry));
        assertEquals(1, list.getWatchingListEntries().size());
        assertEquals(entry.getContentCreatorName(), list.getWatchingListEntries().get(0).getContentCreatorName());
    }

    @Test
    void deleteWatchingListEntry_noExistingEntry_shouldThrow() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", new WatchingListContentAccountId(UUID.randomUUID())));

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, set));
        List<WatchingListEntry> entryList = watchingList.getWatchingListEntries();
        assertEquals(1, entryList.size());

        assertThrows(WatchingListEntryNotExistsException.class, () ->
                watchingList.deleteWatchingListEntry("another name"));
    }

    @Test
    void deleteWatchingListEntry_valid_noEntries_remained() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", new WatchingListContentAccountId(UUID.randomUUID())));

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, set));
        List<WatchingListEntry> entryList = watchingList.getWatchingListEntries();
        assertEquals(1, entryList.size());

        assertDoesNotThrow(() ->
                watchingList.deleteWatchingListEntry(name));

        assertTrue(watchingList.getWatchingListEntries().isEmpty());
    }

    @Test
    void deleteWatchingListEntry_valid_deleteSeveralAccounts() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of(
                new ContentAccountEntry("test", new WatchingListContentAccountId(UUID.randomUUID())),
                new ContentAccountEntry("test1", new WatchingListContentAccountId(UUID.randomUUID()))
        );

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, set));
        List<WatchingListEntry> entryList = watchingList.getWatchingListEntries();
        assertEquals(1, entryList.size());

        assertDoesNotThrow(() ->
                watchingList.deleteWatchingListEntry(name));

        assertTrue(watchingList.getWatchingListEntries().isEmpty());
    }

    @Test
    void deleteWatchingListEntry_valid() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", new WatchingListContentAccountId(UUID.randomUUID())));

        String anotherName = "test";

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, set));
        List<WatchingListEntry> entryList = watchingList.getWatchingListEntries();
        assertEquals(1, entryList.size());

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(anotherName, set));
        entryList = watchingList.getWatchingListEntries();
        assertEquals(2, entryList.size());

        assertDoesNotThrow(() ->
                watchingList.deleteWatchingListEntry(name));

        assertEquals(1, watchingList.getWatchingListEntries().size());
        assertEquals(anotherName, watchingList.getWatchingListEntries().get(0).getContentCreatorName());
        assertEquals(set, watchingList.getWatchingListEntries().get(0).getContentAccountSet());
    }

    @Test
    void updateWatchingListEntryName_notFound_shouldThrow() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());
        assertTrue(watchingList.getWatchingListEntries().isEmpty());
        assertThrows(WatchingListEntryNotExistsException.class, () ->
                watchingList.updateWatchingListEntryName("oldName", "newName"));
    }

    @Test
    void updateWatchingListEntryName_valid() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID(), List.of(
                new WatchingListEntry(new ContentCreator("toChange"), Set.of()),
                new WatchingListEntry(new ContentCreator("unrelated"), Set.of())
        ));

        assertEquals(2, watchingList.getWatchingListEntries().size());
        assertDoesNotThrow(() -> watchingList.updateWatchingListEntryName("toChange", "newName"));
        assertEquals(2, watchingList.getWatchingListEntries().size());
        assertTrue(watchingList.getWatchingListEntries().stream().anyMatch(watchingListEntry -> watchingListEntry.getContentCreatorName().equals("newName")));
        assertTrue(watchingList.getWatchingListEntries().stream().anyMatch(watchingListEntry -> watchingListEntry.getContentCreatorName().equals("unrelated")));
        assertFalse(watchingList.getWatchingListEntries().stream().anyMatch(watchingListEntry -> watchingListEntry.getContentCreatorName().equals("toChange")));
    }
}