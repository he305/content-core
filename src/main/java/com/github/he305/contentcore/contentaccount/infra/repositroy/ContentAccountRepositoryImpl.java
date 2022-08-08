package com.github.he305.contentcore.contentaccount.infra.repositroy;

import com.github.he305.contentcore.contentaccount.domain.model.ContentAccount;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.contentaccount.domain.repository.ContentAccountRepository;
import com.github.he305.contentcore.contentaccount.infra.data.ContentAccountData;
import com.github.he305.contentcore.contentaccount.infra.jpa.JpaContentAccountRepository;
import com.github.he305.contentcore.contentaccount.infra.mapper.ContentAccountDataMapper;
import com.github.he305.contentcore.shared.events.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ContentAccountRepositoryImpl implements ContentAccountRepository {
    private final JpaContentAccountRepository jpaContentAccountRepository;
    private final ContentAccountDataMapper contentAccountDataMapper;
    private final EventPublisher eventPublisher;

    @Override
    public void save(ContentAccount contentAccount) {
        ContentAccountData data = contentAccountDataMapper.toJpa(contentAccount);
        jpaContentAccountRepository.save(data);
        eventPublisher.publishEvent(contentAccount.getEvents());
    }

    @Override
    public ContentAccount getById(UUID id) {
        Optional<ContentAccountData> data = jpaContentAccountRepository.findById(id);
        if (data.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return contentAccountDataMapper.toDomain(data.get());
    }

    @Override
    public Optional<ContentAccount> getByContentAccountDetails(ContentAccountDetails details) {
        Optional<ContentAccountData> data = jpaContentAccountRepository.findByNameAndPlatform(details.getName(), details.getPlatform());
        if (data.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(contentAccountDataMapper.toDomain(data.get()));
    }
}
