package com.github.he305.contentcore.stream.infra.mapper;

import com.github.he305.contentcore.stream.domain.model.entities.StreamData;
import com.github.he305.contentcore.stream.infra.data.StreamDataJpa;
import com.github.he305.contentcore.stream.infra.data.StreamJpa;

public interface StreamDataJpaMapper {
    StreamDataJpa toJpa(StreamData data, StreamJpa streamJpa);

    StreamData toDomain(StreamDataJpa jpa);
}
