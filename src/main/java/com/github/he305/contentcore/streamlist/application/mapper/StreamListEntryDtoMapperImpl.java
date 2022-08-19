package com.github.he305.contentcore.streamlist.application.mapper;

import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.streamlist.application.dto.StreamListEntryDto;
import com.github.he305.contentcore.streamlist.application.dto.StreamListEntryLastDataDto;
import com.github.he305.contentcore.streamlist.application.exchange.StreamListContentAccountExchangeService;
import com.github.he305.contentcore.streamlist.application.exchange.StreamListStreamExchangeService;
import com.github.he305.contentcore.streamlist.domain.model.values.StreamChannelId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreamListEntryDtoMapperImpl implements StreamListEntryDtoMapper {

    private final StreamListContentAccountExchangeService contentAccountExchangeService;
    private final StreamListStreamExchangeService streamListStreamExchangeService;

    @Override
    public StreamListEntryDto toDto(StreamChannelId id) {
        ContentAccountDetails account = contentAccountExchangeService.getContentAccount(id.getId());
        boolean isLive = streamListStreamExchangeService.getIsLive(id.getId());
        StreamListEntryLastDataDto dataDto = streamListStreamExchangeService.getLastData(id.getId());
        String url = contentAccountExchangeService.getUrlContentAccount(account);

        return new StreamListEntryDto(
                account.getName(),
                StreamChannelPlatform.valueOf(account.getPlatform().name()),
                isLive,
                url,
                dataDto
        );
    }
}
