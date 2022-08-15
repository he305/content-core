package com.github.he305.contentcore.streamlist.application.mapper;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.streamlist.application.dto.StreamListEntryDto;
import com.github.he305.contentcore.streamlist.application.dto.StreamListEntryLastDataDto;
import com.github.he305.contentcore.streamlist.application.exchange.ContentAccountExchangeService;
import com.github.he305.contentcore.streamlist.application.exchange.StreamListStreamExchangeService;
import com.github.he305.contentcore.streamlist.domain.model.values.StreamChannelId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StreamListEntryDtoMapperImplTest {

    @Mock
    private ContentAccountExchangeService contentAccountExchangeService;
    @Mock
    private StreamListStreamExchangeService streamListStreamExchangeService;

    @InjectMocks
    private StreamListEntryDtoMapperImpl underTest;

    @Test
    void toDto() {
        UUID id = UUID.randomUUID();
        StreamChannelId channelId = new StreamChannelId(id);
        ContentAccountDetails account = new ContentAccountDetails("name", Platform.TWITCH);
        boolean isLive = true;
        StreamListEntryLastDataDto dataDto = StreamListEntryLastDataDto.empty();
        Mockito.when(contentAccountExchangeService.getContentAccount(id)).thenReturn(account);
        Mockito.when(streamListStreamExchangeService.getIsLive(id)).thenReturn(isLive);
        Mockito.when(streamListStreamExchangeService.getLastData(id)).thenReturn(dataDto);

        StreamListEntryDto expected = new StreamListEntryDto(account.getName(), StreamChannelPlatform.TWITCH, isLive, dataDto);
        StreamListEntryDto actual = underTest.toDto(channelId);
        assertEquals(expected, actual);
    }
}