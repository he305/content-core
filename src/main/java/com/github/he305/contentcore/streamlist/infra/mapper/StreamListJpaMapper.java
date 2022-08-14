package com.github.he305.contentcore.streamlist.infra.mapper;

import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import com.github.he305.contentcore.streamlist.infra.data.StreamListJpa;

public interface StreamListJpaMapper {
    StreamListJpa toJpa(StreamList streamList);

    StreamList toDomain(StreamListJpa jpa);
}
