package com.github.he305.contentcore.contentaccount.application.services;

import com.github.he305.contentcore.contentaccount.application.query.GetContentAccountDetailsQuery;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.contentaccount.domain.service.ContentAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GetContentAccountDetailsServiceImplTest {

    @Mock
    private ContentAccountService contentAccountService;

    @InjectMocks
    private GetContentAccountDetailsServiceImpl underTest;

    @Test
    void execute() {
        UUID id = UUID.randomUUID();
        GetContentAccountDetailsQuery query = new GetContentAccountDetailsQuery(id);
        ContentAccountDetails expected = new ContentAccountDetails("name", Platform.TWITCH);
        Mockito.when(contentAccountService.getContentAccountDetailsById(id)).thenReturn(expected);

        ContentAccountDetails actual = underTest.execute(query);
        assertEquals(expected, actual);
    }
}