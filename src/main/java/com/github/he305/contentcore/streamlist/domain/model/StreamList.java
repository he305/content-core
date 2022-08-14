package com.github.he305.contentcore.streamlist.domain.model;

import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;
import com.github.he305.contentcore.streamlist.domain.model.values.StreamChannelId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class StreamList extends AbstractAggregateRoot<StreamList> {
    private final UUID id;

    private final MemberId memberId;
    private final Set<StreamChannelId> streamChannelIdList;

    public StreamList(UUID memberId) {
        this.id = UUID.randomUUID();
        this.memberId = new MemberId(memberId);
        this.streamChannelIdList = new HashSet<>();
    }

    public StreamList(UUID id, MemberId memberId, Set<StreamChannelId> streamChannelIdList) {
        this.id = id;
        this.memberId = memberId;
        this.streamChannelIdList = streamChannelIdList;
    }

    public void addStreamChannelId(UUID streamChannelId) {
        StreamChannelId toAdd = new StreamChannelId(streamChannelId);
        streamChannelIdList.add(toAdd);
    }

    public void removeStreamChannelId(UUID streamChannelId) {
        StreamChannelId toDelete = new StreamChannelId(streamChannelId);
        streamChannelIdList.remove(toDelete);
    }

    public Collection<Object> getEvents() {
        return domainEvents();
    }
}
