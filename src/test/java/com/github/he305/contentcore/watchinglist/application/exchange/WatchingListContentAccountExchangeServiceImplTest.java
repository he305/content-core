package com.github.he305.contentcore.watchinglist.application.exchange;

import com.github.he305.contentcore.contentaccount.application.exchange.ContentAccountExchangeService;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
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
class WatchingListContentAccountExchangeServiceImplTest {

    @Mock
    private ContentAccountExchangeService contentAccountExchangeService;

    @InjectMocks
    private WatchingListContentAccountExchangeServiceImpl underTest;

    @Test
    void getContentAccountId() {
        UUID id = UUID.randomUUID();
        WatchingListContentAccount account = new WatchingListContentAccount("name", ContentAccountPlatform.TWITCH);
        Mockito.when(contentAccountExchangeService.getContentAccountId(account)).thenReturn(id);

        UUID actual = underTest.getContentAccountId(account);
        assertEquals(id, actual);
    }

    @Test
    void getContentAccount() {
        UUID id = UUID.randomUUID();
        ContentAccountDetails details = new ContentAccountDetails("name", Platform.TWITCH);
        Mockito.when(contentAccountExchangeService.getContentAccountById(id)).thenReturn(details);

        ContentAccountDetails actual = underTest.getContentAccount(id);
        assertEquals(details, actual);
    }
}