package com.github.he305.contentcore.watchinglist.domain.model.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ContentAccountTest {

    @Test
    void testEquals_equals() {
        ContentAccount first = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        ContentAccount second = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        assertEquals(first, second);
    }

    @Test
    void testEquals_notEquals() {
        ContentAccount first = new ContentAccount("name", ContentAccountPlatform.WASD);
        ContentAccount second = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        assertNotEquals(first, second);
    }

    @Test
    void testHashCode_equals() {
        ContentAccount first = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        ContentAccount second = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        assertEquals(first.hashCode(), second.hashCode());
    }
}