package com.github.he305.contentcore.stream.domain.repository;

import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelStatus;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;

import java.util.List;
import java.util.UUID;

public interface StreamChannelRepository {
    void save(StreamChannel streamChannel);

    StreamChannel getById(UUID id);

    StreamChannel getByContentAccountId(StreamChannelContentAccountId id);

    List<StreamChannel> getByStreamContentStatus(StreamChannelStatus status);
}
