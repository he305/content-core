package com.github.he305.contentcore.watchinglist.application.mapper.query;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.watchinglist.application.dto.query.GetContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.exchange.WatchingListContentAccountExchangeService;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import com.github.he305.contentcore.watchinglist.domain.model.values.WatchingListContentAccountId;
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
class GetWatchingListContentAccountDtoMapperImplTest {

    @Mock
    private WatchingListContentAccountExchangeService watchingListContentAccountExchangeService;

    @InjectMocks
    private GetContentAccountDtoMapperImpl underTest;

    @Test
    void toDto() {
        WatchingListContentAccountId id = new WatchingListContentAccountId(UUID.randomUUID());
        ContentAccountEntry entry = new ContentAccountEntry(
                UUID.randomUUID(),
                "test",
                id,
                Set.of(
                        new NotificationId(UUID.randomUUID()),
                        new NotificationId(UUID.randomUUID()),
                        new NotificationId(UUID.randomUUID())
                )
        );

        ContentAccountDetails account = new ContentAccountDetails("name", Platform.TWITCH);
        Mockito.when(watchingListContentAccountExchangeService.getContentAccount(id.getId())).thenReturn(account);
        GetContentAccountDto expected = new GetContentAccountDto(
                "test",
                "name",
                ContentAccountPlatform.TWITCH,
                3
        );

        GetContentAccountDto actual = underTest.toDto(entry);
        assertEquals(expected, actual);
    }
}