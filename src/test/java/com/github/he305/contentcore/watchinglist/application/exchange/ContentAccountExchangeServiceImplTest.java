package com.github.he305.contentcore.watchinglist.application.exchange;

import com.github.he305.contentcore.contentaccount.application.exchange.ContentAccountExchangeService;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ContentAccountExchangeServiceImplTest {

    @Mock
    private ContentAccountExchangeService contentAccountExchangeService;

    @InjectMocks
    private ContentAccountExchangeServiceImpl underTest;

    @Test
    void getContentAccountId() {
        UUID id = UUID.randomUUID();
        ContentAccount account = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        ContentAccountId expected = new ContentAccountId(id);
        Mockito.when(contentAccountExchangeService.getContentAccountId(account)).thenReturn(expected);

        ContentAccountId actual = underTest.getContentAccountId(account);
        assertEquals(expected, actual);
    }

    @Test
    void getContentAccount() {
        UUID id = UUID.randomUUID();
        ContentAccountId data = new ContentAccountId(id);
        ContentAccount expected = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        Mockito.when(contentAccountExchangeService.getContentAccountById(id)).thenReturn(expected);

        ContentAccount actual = underTest.getContentAccount(data);
        assertEquals(expected, actual);
    }
}