package com.github.he305.contentcore.streamlist.infra.mapper;

import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;
import com.github.he305.contentcore.streamlist.domain.model.values.StreamChannelId;
import com.github.he305.contentcore.streamlist.infra.data.StreamChannelIdJpa;
import com.github.he305.contentcore.streamlist.infra.data.StreamListJpa;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StreamListJpaMapperImpl implements StreamListJpaMapper {
    @Override
    public StreamListJpa toJpa(StreamList streamList) {
        Set<StreamChannelIdJpa> streamChannelIdJpaSet = streamList.getStreamChannelIdList().stream()
                .map(streamChannelId -> new StreamChannelIdJpa(streamChannelId.getId()))
                .collect(Collectors.toSet());

        return new StreamListJpa(
                streamList.getId(),
                streamList.getMemberId().getId(),
                streamChannelIdJpaSet
        );
    }

    @Override
    public StreamList toDomain(StreamListJpa jpa) {
        Set<StreamChannelId> streamChannelIdSet = jpa.getStreamChannelIdJpaSet().stream()
                .map(streamChannelIdJpa -> new StreamChannelId(streamChannelIdJpa.getId()))
                .collect(Collectors.toSet());

        return new StreamList(
                jpa.getId(),
                new MemberId(jpa.getMemberId()),
                streamChannelIdSet
        );
    }
}
