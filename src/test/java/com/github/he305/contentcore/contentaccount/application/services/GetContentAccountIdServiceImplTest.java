package com.github.he305.contentcore.contentaccount.application.services;

import com.github.he305.contentcore.contentaccount.application.query.GetContentAccountIdQuery;
import com.github.he305.contentcore.contentaccount.domain.model.ContentAccount;
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
class GetContentAccountIdServiceImplTest {

    @Mock
    private ContentAccountService contentAccountService;

    @InjectMocks
    private GetContentAccountIdServiceImpl underTest;


    @Test
    void execute() {
        UUID id = UUID.randomUUID();
        GetContentAccountIdQuery query = new GetContentAccountIdQuery("name", Platform.TWITCH);
        ContentAccountDetails data = new ContentAccountDetails("name", Platform.TWITCH);
        ContentAccount returned = new ContentAccount(id, data);
        Mockito.when(contentAccountService.getContentAccountOrCreate(data)).thenReturn(returned);

        UUID actual = underTest.execute(query);
        assertEquals(id, actual);
    }
}