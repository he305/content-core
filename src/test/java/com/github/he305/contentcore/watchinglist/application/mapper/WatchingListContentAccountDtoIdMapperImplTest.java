package com.github.he305.contentcore.watchinglist.application.mapper;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.exchange.WatchingListContentAccount;
import com.github.he305.contentcore.watchinglist.application.exchange.WatchingListContentAccountExchangeService;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import com.github.he305.contentcore.watchinglist.domain.model.values.WatchingListContentAccountId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WatchingListContentAccountDtoIdMapperImplTest {

    @Mock
    private WatchingListContentAccountExchangeService watchingListContentAccountExchangeService;

    @InjectMocks
    private ContentAccountDtoIdMapperImpl underTest;


    @Test
    void toContentAccountId() {
        ContentAccountDto data = new ContentAccountDto("entry", "name", ContentAccountPlatform.TWITCH);
        WatchingListContentAccount mapped = new WatchingListContentAccount("name", ContentAccountPlatform.TWITCH);
        UUID watchingListContentAccountId = UUID.randomUUID();
        ContentAccountEntry expected = new ContentAccountEntry("entry", new WatchingListContentAccountId(watchingListContentAccountId));
        Mockito.when(watchingListContentAccountExchangeService.getContentAccountId(mapped)).thenReturn(watchingListContentAccountId);

        ContentAccountEntry actual = underTest.toContentAccountEntry(data);
        assertEquals(expected.getWatchingListContentAccountId(), actual.getWatchingListContentAccountId());
        assertEquals(expected.getAlias(), actual.getAlias());
        assertEquals(expected.getNotificationIds(), actual.getNotificationIds());
    }

    @Test
    void toContentAccountDto() {
        WatchingListContentAccountId data = new WatchingListContentAccountId(UUID.randomUUID());
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry("test", data);
        ContentAccountDetails mapped = new ContentAccountDetails("name", Platform.TWITCH);
        ContentAccountDto expected = new ContentAccountDto("test", "name", ContentAccountPlatform.TWITCH);
        Mockito.when(watchingListContentAccountExchangeService.getContentAccount(data.getId())).thenReturn(mapped);

        ContentAccountDto actual = underTest.toContentAccountDto(contentAccountEntry);
        assertEquals(expected, actual);
    }
}