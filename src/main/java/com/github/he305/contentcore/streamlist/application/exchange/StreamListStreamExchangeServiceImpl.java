package com.github.he305.contentcore.streamlist.application.exchange;

import com.github.he305.contentcore.stream.domain.model.entities.StreamData;
import com.github.he305.contentcore.streamlist.application.dto.StreamListEntryLastDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StreamListStreamExchangeServiceImpl implements StreamListStreamExchangeService {
    private final com.github.he305.contentcore.stream.application.exchange.StreamExchangeService streamExchangeService;

    @Override
    public boolean getIsLive(UUID streamChannelId) {
        return streamExchangeService.getIsLive(streamChannelId);
    }

    @Override
    public StreamListEntryLastDataDto getLastData(UUID streamChannelId) {
        StreamData streamData = streamExchangeService.getLastLiveStreamData(streamChannelId);
        if (streamData == null) {
            return StreamListEntryLastDataDto.empty();
        }
        return new StreamListEntryLastDataDto(
                streamData.getTitle(),
                streamData.getGameName(),
                streamData.getViewerCount()
        );
    }
}
