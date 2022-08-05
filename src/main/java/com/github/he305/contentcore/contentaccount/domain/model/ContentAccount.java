package com.github.he305.contentcore.contentaccount.domain.model;

import com.github.he305.contentcore.contentaccount.domain.events.ContentAccountBecameActiveEvent;
import com.github.he305.contentcore.contentaccount.domain.events.ContentAccountBecameFrozenEvent;
import com.github.he305.contentcore.contentaccount.domain.events.NewContentAccountAddedEvent;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Status;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.contentaccount.domain.model.values.UseCounter;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Getter
public class ContentAccount extends AbstractAggregateRoot<ContentAccount> {
    private final UUID id;

    private final ContentAccountDetails details;

    private final UseCounter useCounter;
    private Status status;

    public ContentAccount(UUID id, ContentAccountDetails details) {
        this.id = id;
        this.details = details;
        this.useCounter = new UseCounter();
        this.status = Status.FROZEN;
        registerEvent(new NewContentAccountAddedEvent(id, details.getPlatform()));
    }

    public ContentAccount(UUID id, ContentAccountDetails details, UseCounter useCounter, Status status) {
        this.id = id;
        this.details = details;
        this.useCounter = useCounter;
        this.status = status;
    }

    public void increaseUseCounter() {
        int oldCounter = useCounter.getCounter();
        useCounter.increaseUseCounter();
        checkContentAccountStatus(oldCounter);
    }

    public void decreaseUseCounter() {
        int oldCounter = useCounter.getCounter();
        useCounter.decreaseUseCounter();
        checkContentAccountStatus(oldCounter);
    }

    private void checkContentAccountStatus(int oldCounter) {
        int newCounter = useCounter.getCounter();

        if (newCounter > 0 && oldCounter == 0) {
            status = Status.ACTIVE;
            registerEvent(new ContentAccountBecameActiveEvent(this.id));
        } else if (newCounter == 0 && oldCounter > 0) {
            status = Status.FROZEN;
            registerEvent(new ContentAccountBecameFrozenEvent(this.id));
        }
    }

    public Collection<Object> getEvents() {
        return domainEvents();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentAccount account = (ContentAccount) o;
        return id.equals(account.id) && details.equals(account.details) && useCounter.equals(account.useCounter) && status == account.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, details, useCounter, status);
    }
}
