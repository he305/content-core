package com.github.he305.contentcore.stream.application.exchange;

import com.github.he305.contentcore.stream.domain.exceptions.NoLiveStreamFoundException;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.entities.StreamData;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
import com.github.he305.contentcore.stream.domain.repository.StreamChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StreamExchangeServiceImpl implements StreamExchangeService {
    private final StreamChannelRepository streamChannelRepository;

    @Override
    public boolean getIsLive(UUID streamChannelId) {
        StreamChannel streamChannel = streamChannelRepository.getByContentAccountId(new StreamChannelContentAccountId(streamChannelId));
        return streamChannel.isLive();
    }

    @Override
    public StreamData getLastLiveStreamData(UUID streamChannelId) {
        StreamChannel streamChannel = streamChannelRepository.getByContentAccountId(new StreamChannelContentAccountId(streamChannelId));
        try {
            return streamChannel.getLastLiveStreamData();
        } catch (NoLiveStreamFoundException ex) {
            return null;
        }
    }
}
