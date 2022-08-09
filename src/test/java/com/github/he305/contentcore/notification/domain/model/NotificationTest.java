package com.github.he305.contentcore.notification.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class NotificationTest {

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Notification.class).verify();
    }
}