package com.github.he305.contentcore.watchinglist.domain.model.entities;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentCreator;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WatchingListEntryTest {

    @Test
    void testEquals_equals() {
        UUID id = UUID.randomUUID();
        UUID contentAccountId = UUID.randomUUID();
        WatchingListEntry first = new WatchingListEntry(id, new ContentCreator("name"), Set.of(new ContentAccountId(contentAccountId)));
        WatchingListEntry second = new WatchingListEntry(id, new ContentCreator("name"), Set.of(new ContentAccountId(contentAccountId)));
        assertEquals(first, second);
    }

    @Test
    void testEquals_notEquals() {
        UUID id = UUID.randomUUID();
        WatchingListEntry first = new WatchingListEntry(new ContentCreator("name1"), Set.of(new ContentAccountId(id)));
        WatchingListEntry second = new WatchingListEntry(new ContentCreator("name"), Set.of(new ContentAccountId(id)));
        assertNotEquals(first, second);
    }

    @Test
    void testHashCode_equals() {
        UUID id = UUID.randomUUID();
        WatchingListEntry first = new WatchingListEntry(id, new ContentCreator("name"), Set.of(new ContentAccountId(id)));
        WatchingListEntry second = new WatchingListEntry(id, new ContentCreator("name"), Set.of(new ContentAccountId(id)));
        assertEquals(first.hashCode(), second.hashCode());
    }
}