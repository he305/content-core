package com.github.he305.contentcore.contentaccount.application.exchange;

import com.github.he305.contentcore.contentaccount.application.query.GetContentAccountDetailsQuery;
import com.github.he305.contentcore.contentaccount.application.query.GetContentAccountIdQuery;
import com.github.he305.contentcore.contentaccount.application.services.GetContentAccountDetailsService;
import com.github.he305.contentcore.contentaccount.application.services.GetContentAccountIdService;
import com.github.he305.contentcore.contentaccount.application.services.GetContentAccountUrlService;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.watchinglist.application.exchange.WatchingListContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ContentAccountExchangeServiceTest {

    @Mock
    private GetContentAccountIdService getContentAccountIdService;
    @Mock
    private GetContentAccountDetailsService getContentAccountDetailsService;
    @Mock
    private GetContentAccountUrlService getContentAccountUrlService;

    @InjectMocks
    private ContentAccountExchangeService underTest;


    @Test
    void getContentAccountId() {
        UUID id = UUID.randomUUID();
        WatchingListContentAccount data = new WatchingListContentAccount("name", ContentAccountPlatform.TWITCH);
        GetContentAccountIdQuery query = new GetContentAccountIdQuery("name", Platform.TWITCH);
        Mockito.when(getContentAccountIdService.execute(query)).thenReturn(id);

        UUID actual = underTest.getContentAccountId(data);
        assertEquals(id, actual);
    }

    @Test
    void getContentAccountId_platformsCheck() {
        Arrays.stream(ContentAccountPlatform.values()).forEach(value -> {
            UUID id = UUID.randomUUID();
            WatchingListContentAccount data = new WatchingListContentAccount("name", value);
            Mockito.when(getContentAccountIdService.execute(Mockito.any())).thenReturn(id);
            assertDoesNotThrow(() -> underTest.getContentAccountId(data));
        });
    }

    @Test
    void getContentAccountById() {
        UUID data = UUID.randomUUID();
        GetContentAccountDetailsQuery query = new GetContentAccountDetailsQuery(data);
        ContentAccountDetails details = new ContentAccountDetails("name", Platform.TWITCH);
        Mockito.when(getContentAccountDetailsService.execute(query)).thenReturn(details);

        ContentAccountDetails actual = underTest.getContentAccountById(data);
        assertEquals(details, actual);
    }

    @Test
    void getContentAccountById_platformsCheck() {
        Arrays.stream(Platform.values()).forEach(value -> {
            UUID data = UUID.randomUUID();
            GetContentAccountDetailsQuery query = new GetContentAccountDetailsQuery(data);
            ContentAccountDetails details = new ContentAccountDetails("name", value);
            Mockito.when(getContentAccountDetailsService.execute(query)).thenReturn(details);
            assertDoesNotThrow(() -> underTest.getContentAccountById(data));
        });
    }

    @Test
    void getUrlForContentAccount_valid() {
        ContentAccountDetails details = new ContentAccountDetails("name", Platform.TWITCH);
        String expected = "some url";
        Mockito.when(getContentAccountUrlService.getUrL(details)).thenReturn(expected);
        String actual = underTest.getUrlForContentAccount(details);
        assertEquals(expected, actual);
    }
}