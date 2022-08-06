package com.github.he305.contentcore.stream.application.mapper;

import com.github.he305.contentcore.contentaccount.application.exchange.ContentAccountExchangeService;
import com.github.he305.contentcore.stream.application.dto.StreamChannelDto;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StreamChannelDtoMapperImplTest {

    @Mock
    private ContentAccountExchangeService contentAccountExchangeService;

    @InjectMocks
    private StreamChannelDtoMapperImpl underTest;

    @Test
    void toDto() {
        UUID id = UUID.randomUUID();
        StreamChannel streamChannel = new StreamChannel(new StreamChannelContentAccountId(id), StreamChannelPlatform.TWITCH);
        ContentAccount contentAccount = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        Mockito.when(contentAccountExchangeService.getContentAccountById(id)).thenReturn(contentAccount);

        StreamChannelDto expected = new StreamChannelDto(
                UUID.randomUUID(),
                "name",
                StreamChannelPlatform.TWITCH,
                false
        );

        StreamChannelDto actual = underTest.toDto(streamChannel);
        assertEquals(expected.getChannelName(), actual.getChannelName());
        assertEquals(expected.getPlatform(), actual.getPlatform());
        assertEquals(expected.isLive(), actual.isLive());
    }
}