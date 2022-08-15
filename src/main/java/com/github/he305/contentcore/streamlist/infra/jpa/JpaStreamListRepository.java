package com.github.he305.contentcore.streamlist.infra.jpa;

import com.github.he305.contentcore.streamlist.infra.data.StreamListJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaStreamListRepository extends JpaRepository<StreamListJpa, UUID> {
    Optional<StreamListJpa> findByMemberId(UUID memberId);
}
