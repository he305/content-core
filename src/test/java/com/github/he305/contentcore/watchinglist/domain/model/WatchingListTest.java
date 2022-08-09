package com.github.he305.contentcore.watchinglist.domain.model;

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
        Set<ContentAccountId> set = Set.of(new ContentAccountId(UUID.randomUUID()));

        assertThrows(IllegalArgumentException.class, () ->
                watchingList.updateWatchingListEntry(name, set));
    }

    @Test
    void updateWatchingListEntry_emptySet_shouldThrow() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountId> set = Set.of();

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
        Set<ContentAccountId> oldSet = Set.of(existing, intersects);
        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, oldSet));

        Set<ContentAccountId> newSet = Set.of(intersects, newId);

        assertDoesNotThrow(() -> watchingList.updateWatchingListEntry(name, newSet));
        List<WatchingListEntry> entryList = watchingList.getWatchingListEntries();
        assertEquals(1, entryList.size());

        Set<ContentAccountId> resultSet = watchingList.getWatchingListEntries().get(0).getContentAccountIdSet();
        assertEquals(newSet, resultSet);
        assertEquals(name, watchingList.getWatchingListEntries().get(0).getContentCreatorName());
    }

    @Test
    void addWatchingListEntry_emptySet_shouldThrow() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountId> set = Set.of();

        assertThrows(IllegalArgumentException.class, () ->
                watchingList.addWatchingListEntry(name, set));
    }

    @Test
    void addWatchingListEntry_entryAlreadyExists_shouldThrow() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountId> set = Set.of(new ContentAccountId(UUID.randomUUID()));

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, set));
        assertThrows(IllegalArgumentException.class, () ->
                watchingList.addWatchingListEntry(name, set));
    }

    @Test
    void addWatchingListEntry_valid() {
        WatchingList watchingList = new WatchingList(UUID.randomUUID(), UUID.randomUUID());

        String name = "testName";
        Set<ContentAccountId> set = Set.of(new ContentAccountId(UUID.randomUUID()));

        assertDoesNotThrow(() -> watchingList.addWatchingListEntry(name, set));
        List<WatchingListEntry> entryList = watchingList.getWatchingListEntries();
        assertEquals(1, entryList.size());

        Set<ContentAccountId> newSet = watchingList.getWatchingListEntries().get(0).getContentAccountIdSet();
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
}