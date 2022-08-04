package com.github.he305.contentcore.watchinglist.domain.model.values;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MemberIdTest {

    @Test
    void testEquals_equals() {
        UUID id = UUID.randomUUID();
        MemberId first = new MemberId(id);
        MemberId second = new MemberId(id);
        assertEquals(first, second);
    }

    @Test
    void testEquals_notEquals() {
        MemberId first = new MemberId(UUID.randomUUID());
        MemberId second = new MemberId(UUID.randomUUID());
        assertNotEquals(first, second);
    }

    @Test
    void testHashCode_equals() {
        UUID id = UUID.randomUUID();
        MemberId first = new MemberId(id);
        MemberId second = new MemberId(id);
        assertEquals(first.hashCode(), second.hashCode());
    }
}