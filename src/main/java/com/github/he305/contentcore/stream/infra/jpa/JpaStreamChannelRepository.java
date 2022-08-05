package com.github.he305.contentcore.stream.infra.jpa;

import com.github.he305.contentcore.stream.infra.data.StreamChannelJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaStreamChannelRepository extends JpaRepository<StreamChannelJpa, UUID> {
    Optional<StreamChannelJpa> findByContentAccountId(UUID id);
}
