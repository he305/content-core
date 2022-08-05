package com.github.he305.contentcore.stream.domain.repository;

import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;

public interface StreamChannelRepository {
    void save(StreamChannel streamChannel);

    StreamChannel getByContentAccountId(StreamChannelContentAccountId id);
}
