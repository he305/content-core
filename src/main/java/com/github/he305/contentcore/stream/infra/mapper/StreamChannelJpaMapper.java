package com.github.he305.contentcore.stream.infra.mapper;

import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.infra.data.StreamChannelJpa;

public interface StreamChannelJpaMapper {
    StreamChannelJpa toJpa(StreamChannel channel);

    StreamChannel toDomain(StreamChannelJpa jpa);
}
