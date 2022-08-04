package com.github.he305.contentcore.watchinglist.domain.model.values;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ContentAccountIdTest {

    @Test
    void testEquals_equals() {
        UUID id = UUID.randomUUID();
        ContentAccountId first = new ContentAccountId(id);
        ContentAccountId second = new ContentAccountId(id);
        assertEquals(first, second);
    }

    @Test
    void testEquals_notEquals() {
        ContentAccountId first = new ContentAccountId(UUID.randomUUID());
        ContentAccountId second = new ContentAccountId(UUID.randomUUID());
        assertNotEquals(first, second);
    }

    @Test
    void testHashCode() {
        UUID id = UUID.randomUUID();
        ContentAccountId first = new ContentAccountId(id);
        ContentAccountId second = new ContentAccountId(id);
        assertEquals(first.hashCode(), second.hashCode());
    }
}