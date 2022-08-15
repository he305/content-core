package com.github.he305.contentcore.stream.application.exchange;

import com.github.he305.contentcore.stream.domain.model.entities.StreamData;

import java.util.UUID;

public interface StreamExchangeService {
    boolean getIsLive(UUID streamChannelId);

    StreamData getLastLiveStreamData(UUID streamChannelId);
}
