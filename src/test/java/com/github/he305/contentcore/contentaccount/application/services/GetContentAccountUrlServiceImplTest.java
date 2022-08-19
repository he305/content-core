package com.github.he305.contentcore.contentaccount.application.services;

import com.github.he305.contentcore.contentaccount.application.services.urlservices.UrlContentAccountFactory;
import com.github.he305.contentcore.contentaccount.application.services.urlservices.UrlContentAccountService;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetContentAccountUrlServiceImplTest {

    @Mock
    private UrlContentAccountFactory urlContentAccountFactory;

    @InjectMocks
    private GetContentAccountUrlServiceImpl underTest;

    @Test
    void getUrL() {
        ContentAccountDetails details = new ContentAccountDetails("name", Platform.TWITCH);
        UrlContentAccountService service = mock(UrlContentAccountService.class);
        String expected = "url";
        when(service.getUrl("name")).thenReturn(expected);
        when(urlContentAccountFactory.getUrlContentAccountService(Platform.TWITCH)).thenReturn(service);

        String actual = underTest.getUrL(details);
        assertEquals(expected, actual);
    }
}