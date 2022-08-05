package com.github.he305.contentcore.contentaccount.domain.service;

import com.github.he305.contentcore.contentaccount.domain.model.ContentAccount;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.contentaccount.domain.repository.ContentAccountRepository;
import com.github.he305.contentcore.watchinglist.domain.events.ContentAccountAddedEvent;
import com.github.he305.contentcore.watchinglist.domain.events.ContentAccountRemovedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContentAccountServiceImpl implements ContentAccountService {
    private final ContentAccountRepository repository;

    @Override
    public ContentAccount getContentAccountOrCreate(ContentAccountDetails details) {
        Optional<ContentAccount> existingContentAccount = repository.getByContentAccountDetails(details);
        return existingContentAccount.orElseGet(() -> createContentAccount(details));
    }

    @Override
    public ContentAccountDetails getContentAccountDetailsById(UUID id) {
        return repository.getById(id).getDetails();
    }

    private ContentAccount createContentAccount(ContentAccountDetails details) {
        ContentAccount contentAccount = new ContentAccount(UUID.randomUUID(), details);
        repository.save(contentAccount);
        return contentAccount;
    }

    @EventListener
    public void handleContentAccountAddedEvent(ContentAccountAddedEvent event) {
        ContentAccount account = repository.getById(event.getId());
        account.increaseUseCounter();
        repository.save(account);
    }

    @EventListener
    public void handleContentAccountRemovedEvent(ContentAccountRemovedEvent event) {
        ContentAccount account = repository.getById(event.getId());
        account.decreaseUseCounter();
        repository.save(account);
    }
}
