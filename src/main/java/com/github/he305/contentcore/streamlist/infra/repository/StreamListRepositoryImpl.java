package com.github.he305.contentcore.streamlist.infra.repository;

import com.github.he305.contentcore.shared.events.EventPublisher;
import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;
import com.github.he305.contentcore.streamlist.domain.repository.StreamListRepository;
import com.github.he305.contentcore.streamlist.infra.data.StreamListJpa;
import com.github.he305.contentcore.streamlist.infra.jpa.JpaStreamListRepository;
import com.github.he305.contentcore.streamlist.infra.mapper.StreamListJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StreamListRepositoryImpl implements StreamListRepository {
    private final JpaStreamListRepository jpaStreamListRepository;
    private final StreamListJpaMapper streamListJpaMapper;
    private final EventPublisher eventPublisher;

    @Override
    public void save(StreamList streamList) {
        StreamListJpa jpa = streamListJpaMapper.toJpa(streamList);
        jpaStreamListRepository.save(jpa);
        eventPublisher.publishEvent(streamList.getEvents());
    }

    @Override
    public Optional<StreamList> getByMemberId(MemberId memberId) {
        Optional<StreamListJpa> jpa = jpaStreamListRepository.findByMemberId(memberId.getId());
        return jpa.map(streamListJpaMapper::toDomain);
    }
}
