package com.github.he305.contentcore.stream.infra.mapper;

import com.github.he305.contentcore.stream.domain.model.entities.Stream;
import com.github.he305.contentcore.stream.infra.data.StreamChannelJpa;
import com.github.he305.contentcore.stream.infra.data.StreamJpa;

public interface StreamJpaMapper {
    StreamJpa toJpa(Stream data, StreamChannelJpa streamChannelJpa);

    Stream toDomain(StreamJpa jpa);
}
