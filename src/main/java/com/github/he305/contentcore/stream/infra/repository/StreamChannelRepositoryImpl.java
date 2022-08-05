package com.github.he305.contentcore.stream.infra.repository;

import com.github.he305.contentcore.shared.events.EventPublisher;
import com.github.he305.contentcore.stream.domain.exceptions.StreamChannelWithContentAccountIdNotFoundException;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
import com.github.he305.contentcore.stream.domain.repository.StreamChannelRepository;
import com.github.he305.contentcore.stream.infra.data.StreamChannelJpa;
import com.github.he305.contentcore.stream.infra.jpa.JpaStreamChannelRepository;
import com.github.he305.contentcore.stream.infra.mapper.StreamChannelJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class StreamChannelRepositoryImpl implements StreamChannelRepository {
    private final JpaStreamChannelRepository jpaStreamChannelRepository;
    private final StreamChannelJpaMapper streamChannelJpaMapper;
    private final EventPublisher eventPublisher;

    @Override
    public void save(StreamChannel streamChannel) {
        StreamChannelJpa jpa = streamChannelJpaMapper.toJpa(streamChannel);
        jpaStreamChannelRepository.save(jpa);
        eventPublisher.publishEvent(streamChannel.getEvents());
    }

    @Override
    public StreamChannel getByContentAccountId(StreamChannelContentAccountId id) {
        Optional<StreamChannelJpa> jpa = jpaStreamChannelRepository.findByContentAccountId(id.getId());
        if (jpa.isEmpty()) {
            throw new StreamChannelWithContentAccountIdNotFoundException(id);
        }
        return streamChannelJpaMapper.toDomain(jpa.get());
    }
}
