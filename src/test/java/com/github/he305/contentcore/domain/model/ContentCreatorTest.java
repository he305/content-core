package com.github.he305.contentcore.domain.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ContentCreatorTest {

    @Test
    void create_emptyContentList_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new ContentCreator("smth", Collections.emptyList()));
    }

    @Test
    void create_nullName_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new ContentCreator(null, Collections.emptyList()));
    }

    @Test
    void create_blankName_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new ContentCreator("", Collections.emptyList()));
    }

    @Test
    void addContentEntity() {
    }

    @Test
    void removeContentEntity() {
    }

    @Test
    void validateFields() {
    }

    @Test
    void testEquals() {
    }
}