package com.github.he305.contentcore.stream.domain.model.entities;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamDataTest {

    @Test
    void testEquals() {
        EqualsVerifier.forClass(StreamData.class).suppress(Warning.ALL_FIELDS_SHOULD_BE_USED).verify();
    }

    @Test
    void test_toString() {
        LocalDateTime time = LocalDateTime.now();
        StreamData data = new StreamData("long game name", "long title", 322, time);

        String expected = time + ": title: long title, game name: long game name, viewers: 322";
        String actual = data.toString();
        assertEquals(expected, actual);
    }
}