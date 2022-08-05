package com.github.he305.contentcore.stream.infra.mapper;

import com.github.he305.contentcore.stream.domain.model.entities.StreamData;
import com.github.he305.contentcore.stream.infra.data.StreamDataJpa;
import com.github.he305.contentcore.stream.infra.data.StreamJpa;
import org.springframework.stereotype.Component;

@Component
public class StreamDataJpaMapperImpl implements StreamDataJpaMapper {
    @Override
    public StreamDataJpa toJpa(StreamData data, StreamJpa streamJpa) {
        return new StreamDataJpa(
                data.getId(),
                data.getGameName(),
                data.getTitle(),
                data.getViewerCount(),
                data.getStreamDataTime(),
                streamJpa
        );
    }

    @Override
    public StreamData toDomain(StreamDataJpa jpa) {
        return new StreamData(
                jpa.getId(),
                jpa.getGameName(),
                jpa.getTitle(),
                jpa.getViewerCount(),
                jpa.getStreamDataTime()
        );
    }
}
