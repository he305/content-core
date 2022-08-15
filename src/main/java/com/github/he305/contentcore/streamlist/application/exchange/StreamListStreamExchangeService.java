package com.github.he305.contentcore.streamlist.application.exchange;

import com.github.he305.contentcore.streamlist.application.dto.StreamListEntryLastDataDto;

import java.util.UUID;

public interface StreamListStreamExchangeService {
    boolean getIsLive(UUID streamChannelId);

    StreamListEntryLastDataDto getLastData(UUID streamChannelId);
}
