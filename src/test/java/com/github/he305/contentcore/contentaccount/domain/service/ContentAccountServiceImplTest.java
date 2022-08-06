package com.github.he305.contentcore.contentaccount.domain.service;

import com.github.he305.contentcore.contentaccount.domain.model.ContentAccount;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Status;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.contentaccount.domain.model.values.UseCounter;
import com.github.he305.contentcore.contentaccount.domain.repository.ContentAccountRepository;
import com.github.he305.contentcore.watchinglist.domain.events.ContentAccountAddedEvent;
import com.github.he305.contentcore.watchinglist.domain.events.ContentAccountRemovedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ContentAccountServiceImplTest {

    @Mock
    private ContentAccountRepository contentAccountRepository;
    @InjectMocks
    private ContentAccountServiceImpl underTest;

    @Test
    void getContentAccountOrCreate_returnExisting() {
        ContentAccountDetails details = new ContentAccountDetails("name", Platform.TWITCH);
        ContentAccount toReturn = new ContentAccount(UUID.randomUUID(), new ContentAccountDetails("name", Platform.TWITCH));
        Mockito.when(contentAccountRepository.getByContentAccountDetails(details)).thenReturn(Optional.of(toReturn));

        ContentAccount actual = underTest.getContentAccountOrCreate(details);
        assertEquals(toReturn, actual);
    }

    @Test
    void getContentAccountOrCreate_createNew() {
        ContentAccountDetails details = new ContentAccountDetails("name", Platform.TWITCH);
        Mockito.when(contentAccountRepository.getByContentAccountDetails(details)).thenReturn(Optional.empty());

        ContentAccount actual = underTest.getContentAccountOrCreate(details);
        ContentAccount expected = new ContentAccount(actual.getId(), details);
        assertEquals(expected, actual);
    }

    @Test
    void getContentAccountDetailsById() {
        UUID id = UUID.randomUUID();
        ContentAccountDetails details = new ContentAccountDetails("name", Platform.TWITCH);
        ContentAccount toReturn = new ContentAccount(id, details);
        Mockito.when(contentAccountRepository.getById(id)).thenReturn(toReturn);

        ContentAccountDetails actual = underTest.getContentAccountDetailsById(id);
        assertEquals(details, actual);
    }

    @Test
    void handleContentAccountAddedEvent() {
        UUID id = UUID.randomUUID();
        ContentAccountAddedEvent event = new ContentAccountAddedEvent(id);
        ContentAccount account = new ContentAccount(id, new ContentAccountDetails("name", Platform.TWITCH));
        assertEquals(Status.FROZEN, account.getStatus());
        assertEquals(0, account.getUseCounter().getCounter());
        Mockito.when(contentAccountRepository.getById(id)).thenReturn(account);

        assertDoesNotThrow(() -> underTest.handleContentAccountAddedEvent(event));
        assertEquals(Status.ACTIVE, account.getStatus());
        assertEquals(1, account.getUseCounter().getCounter());
    }

    @Test
    void handleContentAccountRemovedEvent() {
        UUID id = UUID.randomUUID();
        ContentAccountRemovedEvent event = new ContentAccountRemovedEvent(id);
        ContentAccount account = new ContentAccount(id, new ContentAccountDetails("name", Platform.TWITCH), new UseCounter(1), Status.ACTIVE);
        assertEquals(Status.ACTIVE, account.getStatus());
        assertEquals(1, account.getUseCounter().getCounter());
        Mockito.when(contentAccountRepository.getById(id)).thenReturn(account);

        assertDoesNotThrow(() -> underTest.handleContentAccountRemovedEvent(event));
        assertEquals(Status.FROZEN, account.getStatus());
        assertEquals(0, account.getUseCounter().getCounter());
    }
}