package com.github.he305.contentcore.watchinglist.domain.model;

import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.entities.WatchingListEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WatchingListTest {

    @Test
    void updateWatchingListEntry_entityNotExists_shouldThrow() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", new ContentAccountId(UUID.randomUUID())));

        assertThrows(IllegalArgumentException.class, () ->
                watchingList.updateWatchingListEntry(name, set));
    }

    @Test
    void updateWatchingListEntry_emptySet_shouldThrow() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of();

        assertThrows(IllegalArgumentException.class, () ->
                watchingList.updateWatchingListEntry(name, set));
    }

    @Test
    void updateWatchingListEntry_valid() {
        ContentAccountId existing = new ContentAccountId(UUID.randomUUID());
        ContentAccountId intersects = new ContentAccountId(UUID.randomUUID());
        ContentAccountId newId = new ContentAccountId(UUID.randomUUID());


        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> oldSet = Set.of(
                new ContentAccountEntry("test", existing),
                new ContentAccountEntry("test", intersects)
        );

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, oldSet));

        Set<ContentAccountEntry> newSet = Set.of(
                new ContentAccountEntry("test", intersects),
                new ContentAccountEntry("test", newId)
        );

        assertDoesNotThrow(() -> watchingList.updateWatchingListEntry(name, newSet));
        List<WatchingListEntry> entryList = watchingList.getWatchingListEntries();
        assertEquals(1, entryList.size());

        Set<ContentAccountEntry> resultSet = watchingList.getWatchingListEntries().get(0).getContentAccountSet();
        assertEquals(newSet, resultSet);
        assertEquals(name, watchingList.getWatchingListEntries().get(0).getContentCreatorName());
    }

    @Test
    void addWatchingListEntry_emptySet_shouldThrow() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of();

        assertThrows(IllegalArgumentException.class, () ->
                watchingList.addWatchingListEntry(name, set));
    }

    @Test
    void addWatchingListEntry_entryAlreadyExists_shouldThrow() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", new ContentAccountId(UUID.randomUUID())));

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, set));
        assertThrows(IllegalArgumentException.class, () ->
                watchingList.addWatchingListEntry(name, set));
    }

    @Test
    void addWatchingListEntry_valid() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", new ContentAccountId(UUID.randomUUID())));

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
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", new ContentAccountId(UUID.randomUUID())));

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, set));
        List<WatchingListEntry> entryList = watchingList.getWatchingListEntries();
        assertEquals(1, entryList.size());

        assertThrows(IllegalArgumentException.class, () ->
                watchingList.deleteWatchingListEntry("another name"));
    }

    @Test
    void deleteWatchingListEntry_valid_noEntries_remained() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", new ContentAccountId(UUID.randomUUID())));

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
        Set<ContentAccountEntry> set = Set.of(new ContentAccountEntry("test", new ContentAccountId(UUID.randomUUID())));

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
}