package com.github.he305.contentcore.watchinglist.domain.model.values;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WatchingListWatchingListContentAccountIdTest {

    @Test
    void testEquals_equals() {
        UUID id = UUID.randomUUID();
        WatchingListContentAccountId first = new WatchingListContentAccountId(id);
        WatchingListContentAccountId second = new WatchingListContentAccountId(id);
        assertEquals(first, second);
    }

    @Test
    void testEquals_notEquals() {
        WatchingListContentAccountId first = new WatchingListContentAccountId(UUID.randomUUID());
        WatchingListContentAccountId second = new WatchingListContentAccountId(UUID.randomUUID());
        assertNotEquals(first, second);
    }

    @Test
    void testHashCode() {
        UUID id = UUID.randomUUID();
        WatchingListContentAccountId first = new WatchingListContentAccountId(id);
        WatchingListContentAccountId second = new WatchingListContentAccountId(id);
        assertEquals(first.hashCode(), second.hashCode());
    }
}