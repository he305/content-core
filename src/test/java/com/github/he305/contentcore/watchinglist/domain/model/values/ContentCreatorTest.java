package com.github.he305.contentcore.watchinglist.domain.model.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ContentCreatorTest {

    @Test
    void testEquals_equals() {
        ContentCreator first = new ContentCreator("name");
        ContentCreator second = new ContentCreator("name");
        assertEquals(first, second);
    }

    @Test
    void testEquals_notEequals() {
        ContentCreator first = new ContentCreator("name1");
        ContentCreator second = new ContentCreator("name");
        assertNotEquals(first, second);
    }

    @Test
    void testHashCode_equals() {
        ContentCreator first = new ContentCreator("name");
        ContentCreator second = new ContentCreator("name");
        assertEquals(first.hashCode(), second.hashCode());
    }
}