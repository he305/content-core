package com.github.he305.contentcore.stream.application.mapper;

import com.github.he305.contentcore.contentaccount.application.exchange.ContentAccountExchangeService;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.stream.application.dto.StreamChannelDto;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreamChannelDtoMapperImpl implements StreamChannelDtoMapper {
    private final ContentAccountExchangeService contentAccountExchangeService;

    @Override
    public StreamChannelDto toDto(StreamChannel streamChannel) {
        ContentAccountDetails details = contentAccountExchangeService.getContentAccountById(streamChannel.getStreamChannelContentAccountId().getId());

        return new StreamChannelDto(
                streamChannel.getId(),
                details.getName(),
                streamChannel.getPlatform(),
                streamChannel.isLive()
        );
    }
}
