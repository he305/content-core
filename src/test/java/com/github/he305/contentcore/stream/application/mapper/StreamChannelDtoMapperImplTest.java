package com.github.he305.contentcore.stream.application.mapper;

import com.github.he305.contentcore.contentaccount.application.exchange.ContentAccountExchangeService;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.stream.application.dto.StreamChannelDto;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
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
        ContentAccountDetails contentAccountDetails = new ContentAccountDetails("name", Platform.TWITCH);
        Mockito.when(contentAccountExchangeService.getContentAccountById(id)).thenReturn(contentAccountDetails);

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