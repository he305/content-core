package com.github.he305.contentcore.stream.infra.mapper;

import com.github.he305.contentcore.stream.domain.model.entities.Stream;
import com.github.he305.contentcore.stream.domain.model.entities.StreamData;
import com.github.he305.contentcore.stream.infra.data.StreamChannelJpa;
import com.github.he305.contentcore.stream.infra.data.StreamDataJpa;
import com.github.he305.contentcore.stream.infra.data.StreamJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StreamJpaMapperImpl implements StreamJpaMapper {
    private final StreamDataJpaMapper streamDataJpaMapper;

    @Override
    public StreamJpa toJpa(Stream data, StreamChannelJpa streamChannelJpa) {
        StreamJpa streamJpa = StreamJpa.builder()
                .id(data.getId())
                .maxViewers(data.getMaxViewers())
                .startedAt(data.getStartedAt())
                .endedAt(data.getEndedAt())
                .build();

        List<StreamDataJpa> streamDataJpaList = data
                .getStreamDataList()
                .stream()
                .map(streamData -> streamDataJpaMapper.toJpa(streamData, streamJpa))
                .collect(Collectors.toList());

        streamJpa.setStreamDatas(streamDataJpaList);
        return streamJpa;
    }

    @Override
    public Stream toDomain(StreamJpa jpa) {
        List<StreamData> streamDataList = jpa
                .getStreamDatas()
                .stream()
                .map(streamDataJpaMapper::toDomain)
                .collect(Collectors.toList());

        return new Stream(
                jpa.getId(),
                jpa.isLive(),
                jpa.getMaxViewers(),
                jpa.getStartedAt(),
                jpa.getEndedAt(),
                streamDataList
        );
    }
}
