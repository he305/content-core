package com.github.he305.contentcore.streamlist.domain.sevice;

import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;
import com.github.he305.contentcore.streamlist.domain.repository.StreamListRepository;
import com.github.he305.contentcore.watchinglist.domain.events.ContentAccountAddedEvent;
import com.github.he305.contentcore.watchinglist.domain.events.ContentAccountRemovedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamListServiceImpl implements StreamListService {

    private final StreamListRepository streamListRepository;

    @EventListener
    public void handleContentAccountAddedEvent(ContentAccountAddedEvent event) {
        Optional<StreamList> optionalStreamList = streamListRepository.getByMemberId(new MemberId(event.getMemberId()));
        StreamList streamList = optionalStreamList.orElseGet(() -> new StreamList(event.getMemberId()));

        streamList.addStreamChannelId(event.getId());
        streamListRepository.save(streamList);
    }

    @EventListener
    public void handleContentAccountRemovedEvent(ContentAccountRemovedEvent event) {
        Optional<StreamList> optionalStreamList = streamListRepository.getByMemberId(new MemberId(event.getMemberId()));
        StreamList streamList = optionalStreamList.orElseGet(() -> new StreamList(event.getMemberId()));

        streamList.removeStreamChannelId(event.getId());
        streamListRepository.save(streamList);
    }
}
