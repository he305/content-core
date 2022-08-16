package com.github.he305.contentcore.streamlist.domain.sevice;

import com.github.he305.contentcore.contentaccount.application.exchange.ContentAccountExchangeService;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;
import com.github.he305.contentcore.streamlist.domain.repository.StreamListRepository;
import com.github.he305.contentcore.streamlist.domain.validators.StreamListPlatformValidator;
import com.github.he305.contentcore.watchinglist.domain.events.WatchingListContentAccountAddedEvent;
import com.github.he305.contentcore.watchinglist.domain.events.WatchingListContentAccountRemovedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamListServiceImpl implements StreamListService {

    private final ContentAccountExchangeService contentAccountExchangeService;
    private final StreamListRepository streamListRepository;
    private final StreamListPlatformValidator streamListPlatformValidator;

    @EventListener
    public void handleWatchingListContentAccountAddedEvent(WatchingListContentAccountAddedEvent event) {
        ContentAccountDetails contentDetails = contentAccountExchangeService.getContentAccountById(event.getContentAccountId());
        if (!streamListPlatformValidator.isStreamChannel(contentDetails.getPlatform())) {
            return;
        }

        Optional<StreamList> optionalStreamList = streamListRepository.getByMemberId(new MemberId(event.getMemberId()));
        StreamList streamList = optionalStreamList.orElseGet(() -> new StreamList(event.getMemberId()));

        streamList.addStreamChannelId(event.getContentAccountId());
        streamListRepository.save(streamList);
    }

    @EventListener
    public void handleWatchingListContentAccountRemovedEvent(WatchingListContentAccountRemovedEvent event) {
        ContentAccountDetails contentDetails = contentAccountExchangeService.getContentAccountById(event.getContentAccountId());
        if (!streamListPlatformValidator.isStreamChannel(contentDetails.getPlatform())) {
            return;
        }

        Optional<StreamList> optionalStreamList = streamListRepository.getByMemberId(new MemberId(event.getMemberId()));
        StreamList streamList = optionalStreamList.orElseGet(() -> new StreamList(event.getMemberId()));

        streamList.removeStreamChannelId(event.getContentAccountId());
        streamListRepository.save(streamList);
    }
}
