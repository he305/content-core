package com.github.he305.contentcore.watchinglist.application.mapper;

import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import com.github.he305.contentcore.watchinglist.domain.service.ContentAccountExchangeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ContentAccountDtoIdMapperImplTest {

    @Mock
    private ContentAccountExchangeService contentAccountExchangeService;

    @InjectMocks
    private ContentAccountDtoIdMapperImpl underTest;


    @Test
    void toContentAccountId() {
        ContentAccountDto data = new ContentAccountDto("entry", "name", ContentAccountPlatform.TWITCH);
        ContentAccount mapped = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        ContentAccountId contentAccountId = new ContentAccountId(UUID.randomUUID());
        ContentAccountEntry expected = new ContentAccountEntry("entry", contentAccountId);
        Mockito.when(contentAccountExchangeService.getContentAccountId(mapped)).thenReturn(contentAccountId);

        ContentAccountEntry actual = underTest.toContentAccountEntry(data);
        assertEquals(expected.getContentAccountId(), actual.getContentAccountId());
        assertEquals(expected.getAlias(), actual.getAlias());
        assertEquals(expected.getNotificationIds(), actual.getNotificationIds());
    }

    @Test
    void toContentAccountDto() {
        ContentAccountId data = new ContentAccountId(UUID.randomUUID());
        ContentAccountEntry contentAccountEntry = new ContentAccountEntry("test", data);
        ContentAccount mapped = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        ContentAccountDto expected = new ContentAccountDto("test", "name", ContentAccountPlatform.TWITCH);
        Mockito.when(contentAccountExchangeService.getContentAccount(data)).thenReturn(mapped);

        ContentAccountDto actual = underTest.toContentAccountDto(contentAccountEntry);
        assertEquals(expected, actual);
    }
}