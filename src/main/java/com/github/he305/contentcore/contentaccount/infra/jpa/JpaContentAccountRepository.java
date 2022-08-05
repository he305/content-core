package com.github.he305.contentcore.contentaccount.infra.jpa;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.infra.data.ContentAccountData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaContentAccountRepository extends JpaRepository<ContentAccountData, UUID> {
    Optional<ContentAccountData> findByNameAndPlatform(String name, Platform platform);
}
