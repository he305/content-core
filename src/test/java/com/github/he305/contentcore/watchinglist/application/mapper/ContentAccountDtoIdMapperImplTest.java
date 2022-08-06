package com.github.he305.contentcore.watchinglist.application.mapper;

import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
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
        ContentAccountDto data = new ContentAccountDto("name", ContentAccountPlatform.TWITCH);
        ContentAccount mapped = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        ContentAccountId expected = new ContentAccountId(UUID.randomUUID());
        Mockito.when(contentAccountExchangeService.getContentAccountId(mapped)).thenReturn(expected);

        ContentAccountId actual = underTest.toContentAccountId(data);
        assertEquals(expected, actual);
    }

    @Test
    void toContentAccountDto() {
        ContentAccountId data = new ContentAccountId(UUID.randomUUID());
        ContentAccount mapped = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        ContentAccountDto expected = new ContentAccountDto("name", ContentAccountPlatform.TWITCH);
        Mockito.when(contentAccountExchangeService.getContentAccount(data)).thenReturn(mapped);

        ContentAccountDto actual = underTest.toContentAccountDto(data);
        assertEquals(expected, actual);
    }
}