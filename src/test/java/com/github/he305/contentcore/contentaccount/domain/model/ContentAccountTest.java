package com.github.he305.contentcore.contentaccount.domain.model;

import com.github.he305.contentcore.contentaccount.domain.events.NewContentAccountAddedEvent;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Status;
import com.github.he305.contentcore.contentaccount.domain.model.exceptions.UseCounterBelowZeroException;
import com.github.he305.contentcore.contentaccount.domain.model.exceptions.UseCounterTooHighException;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.contentaccount.domain.model.values.UseCounter;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ContentAccountTest {

    @Test
    void increaseUseCounter_valid() {
        ContentAccount account = new ContentAccount(UUID.randomUUID(), new ContentAccountDetails("name", Platform.TWITCH));
        assertEquals(Status.FROZEN, account.getStatus());
        assertEquals(0, account.getUseCounter().getCounter());

        account.increaseUseCounter();

        assertEquals(Status.ACTIVE, account.getStatus());
        assertEquals(1, account.getUseCounter().getCounter());

        account.increaseUseCounter();

        assertEquals(Status.ACTIVE, account.getStatus());
        assertEquals(2, account.getUseCounter().getCounter());
    }

    @Test
    void increaseUseCounter_maxValue() {
        ContentAccount account = new ContentAccount(UUID.randomUUID(), new ContentAccountDetails("name", Platform.TWITCH), new UseCounter(Integer.MAX_VALUE), Status.ACTIVE);
        assertEquals(Status.ACTIVE, account.getStatus());
        assertEquals(Integer.MAX_VALUE, account.getUseCounter().getCounter());

        assertThrows(UseCounterTooHighException.class, account::increaseUseCounter);
        assertEquals(Status.ACTIVE, account.getStatus());
        assertEquals(Integer.MAX_VALUE, account.getUseCounter().getCounter());
    }

    @Test
    void decreaseUseCounter() {
        ContentAccount account = new ContentAccount(UUID.randomUUID(), new ContentAccountDetails("name", Platform.TWITCH));
        assertEquals(Status.FROZEN, account.getStatus());
        assertEquals(0, account.getUseCounter().getCounter());

        account.increaseUseCounter();

        assertEquals(Status.ACTIVE, account.getStatus());
        assertEquals(1, account.getUseCounter().getCounter());

        account.decreaseUseCounter();
        assertEquals(Status.FROZEN, account.getStatus());
        assertEquals(0, account.getUseCounter().getCounter());

        assertThrows(UseCounterBelowZeroException.class, account::decreaseUseCounter);
        assertEquals(Status.FROZEN, account.getStatus());
        assertEquals(0, account.getUseCounter().getCounter());
    }

    @Test
    void getEvents_constructorEvent() {
        ContentAccount account = new ContentAccount(UUID.randomUUID(), new ContentAccountDetails("name", Platform.TWITCH));
        Collection<Object> events = account.getEvents();
        assertEquals(1, events.size());
        events.forEach(event -> assertTrue(event instanceof NewContentAccountAddedEvent));
    }

    @Test
    void hashCode_equals() {
        UUID id = UUID.randomUUID();
        ContentAccount first = new ContentAccount(id, new ContentAccountDetails("name", Platform.TWITCH), new UseCounter(255), Status.ACTIVE);
        ContentAccount second = new ContentAccount(id, new ContentAccountDetails("name", Platform.TWITCH), new UseCounter(255), Status.ACTIVE);

        assertEquals(first.hashCode(), second.hashCode());
    }
}