package com.github.he305.contentcore.watchinglist.application.mapper.query;

import com.github.he305.contentcore.watchinglist.application.dto.query.GetContentAccountDto;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import com.github.he305.contentcore.watchinglist.domain.service.ContentAccountExchangeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GetContentAccountDtoMapperImplTest {

    @Mock
    private ContentAccountExchangeService contentAccountExchangeService;

    @InjectMocks
    private GetContentAccountDtoMapperImpl underTest;

    @Test
    void toDto() {
        ContentAccountId id = new ContentAccountId(UUID.randomUUID());
        ContentAccountEntry entry = new ContentAccountEntry(
                UUID.randomUUID(),
                id,
                Set.of(
                        new NotificationId(UUID.randomUUID()),
                        new NotificationId(UUID.randomUUID()),
                        new NotificationId(UUID.randomUUID())
                )
        );

        ContentAccount account = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        Mockito.when(contentAccountExchangeService.getContentAccount(id)).thenReturn(account);
        GetContentAccountDto expected = new GetContentAccountDto(
                "name",
                ContentAccountPlatform.TWITCH,
                3
        );

        GetContentAccountDto actual = underTest.toDto(entry);
        assertEquals(expected, actual);
    }
}