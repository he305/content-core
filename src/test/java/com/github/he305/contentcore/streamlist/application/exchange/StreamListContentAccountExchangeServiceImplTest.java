package com.github.he305.contentcore.streamlist.application.exchange;

import com.github.he305.contentcore.contentaccount.application.exchange.ContentAccountExchangeService;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StreamListContentAccountExchangeServiceImplTest {

    @Mock
    private ContentAccountExchangeService contentAccountExchangeService;

    @InjectMocks
    private StreamListContentAccountExchangeServiceImpl underTest;

    @Test
    void getContentAccount() {
        UUID id = UUID.randomUUID();
        ContentAccountDetails expected = new ContentAccountDetails("name", Platform.TWITCH);
        Mockito.when(contentAccountExchangeService.getContentAccountById(id)).thenReturn(expected);
        ContentAccountDetails actual = underTest.getContentAccount(id);
        assertEquals(expected, actual);
    }

    @Test
    void getUrlContentAccount() {
        ContentAccountDetails accountDetails = new ContentAccountDetails("name", Platform.TWITCH);
        String expected = "url";
        Mockito.when(contentAccountExchangeService.getUrlForContentAccount(accountDetails)).thenReturn(expected);

        String actual = underTest.getUrlContentAccount(accountDetails);
        assertEquals(expected, actual);
    }
}