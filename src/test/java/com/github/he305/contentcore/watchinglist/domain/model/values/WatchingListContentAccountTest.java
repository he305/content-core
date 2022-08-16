package com.github.he305.contentcore.watchinglist.domain.model.values;

import com.github.he305.contentcore.watchinglist.application.exchange.WatchingListContentAccount;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WatchingListContentAccountTest {

    @Test
    void testEquals_verifier() {
        EqualsVerifier.simple().forClass(WatchingListContentAccount.class).verify();
    }

    @Test
    void testEquals_equals() {
        WatchingListContentAccount first = new WatchingListContentAccount("name", ContentAccountPlatform.TWITCH);
        WatchingListContentAccount second = new WatchingListContentAccount("name", ContentAccountPlatform.TWITCH);
        assertEquals(first, second);
    }

    @Test
    void testEquals_notEquals() {
        WatchingListContentAccount first = new WatchingListContentAccount("name", ContentAccountPlatform.WASD);
        WatchingListContentAccount second = new WatchingListContentAccount("name", ContentAccountPlatform.TWITCH);
        assertNotEquals(first, second);
    }

    @Test
    void testHashCode_equals() {
        WatchingListContentAccount first = new WatchingListContentAccount("name", ContentAccountPlatform.TWITCH);
        WatchingListContentAccount second = new WatchingListContentAccount("name", ContentAccountPlatform.TWITCH);
        assertEquals(first.hashCode(), second.hashCode());
    }
}