package com.github.he305.contentcore.stream.infra.mapper;

import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.entities.Stream;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
import com.github.he305.contentcore.stream.infra.data.StreamChannelJpa;
import com.github.he305.contentcore.stream.infra.data.StreamJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StreamChannelJpaMapperImpl implements StreamChannelJpaMapper {
    private final StreamJpaMapper streamJpaMapper;

    @Override
    public StreamChannelJpa toJpa(StreamChannel channel) {
        StreamChannelJpa jpa = StreamChannelJpa
                .builder()
                .id(channel.getId())
                .platform(channel.getPlatform())
                .status(channel.getStatus())
                .contentAccountId(channel.getStreamChannelContentAccountId().getId())
                .build();

        List<StreamJpa> streamJpaList = channel
                .getStreams()
                .stream()
                .map(stream -> streamJpaMapper.toJpa(stream, jpa))
                .collect(Collectors.toList());

        jpa.setStreams(streamJpaList);
        return jpa;
    }

    @Override
    public StreamChannel toDomain(StreamChannelJpa jpa) {
        List<Stream> streams = jpa
                .getStreams()
                .stream()
                .map(streamJpaMapper::toDomain)
                .collect(Collectors.toList());

        return new StreamChannel(
                jpa.getId(),
                new StreamChannelContentAccountId(jpa.getContentAccountId()),
                jpa.getPlatform(),
                streams,
                jpa.getStatus()
        );
    }
}
