package com.github.he305.contentcore.watchinglist.infra.jpa;

import com.github.he305.contentcore.watchinglist.infra.data.WatchingListData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaWatchingListRepository extends JpaRepository<WatchingListData, UUID> {
    Optional<WatchingListData> getByMemberId(UUID id);
}
