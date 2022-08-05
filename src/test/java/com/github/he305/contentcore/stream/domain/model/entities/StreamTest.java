package com.github.he305.contentcore.stream.domain.model.entities;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class StreamTest {

    @Test
    void testEquals() {
        EqualsVerifier.forClass(Stream.class).suppress(Warning.ALL_FIELDS_SHOULD_BE_USED).verify();
    }
}