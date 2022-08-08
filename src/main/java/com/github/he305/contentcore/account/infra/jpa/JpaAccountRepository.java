package com.github.he305.contentcore.account.infra.jpa;

import com.github.he305.contentcore.account.infra.data.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaAccountRepository extends JpaRepository<AccountData, UUID> {
    Optional<AccountData> findByUsername(String username);

    Optional<AccountData> findByUsernameAndPassword(String username, String password);
}
