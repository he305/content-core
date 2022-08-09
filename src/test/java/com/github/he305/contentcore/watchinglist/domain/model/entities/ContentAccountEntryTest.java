package com.github.he305.contentcore.watchinglist.domain.model.entities;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ContentAccountEntryTest {

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(ContentAccountEntry.class).verify();
    }
}