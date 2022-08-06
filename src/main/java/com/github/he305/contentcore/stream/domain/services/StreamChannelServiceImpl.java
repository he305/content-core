package com.github.he305.contentcore.stream.domain.services;

import com.github.he305.contentcore.contentaccount.domain.events.ContentAccountBecameActiveEvent;
import com.github.he305.contentcore.contentaccount.domain.events.ContentAccountBecameFrozenEvent;
import com.github.he305.contentcore.contentaccount.domain.events.NewContentAccountAddedEvent;
import com.github.he305.contentcore.stream.domain.exceptions.ErrorCreatingStreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.exceptions.StreamChannelWithContentAccountIdNotFoundException;
import com.github.he305.contentcore.stream.domain.mapper.StreamChannelPlatformMapper;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.values.StreamChannelContentAccountId;
import com.github.he305.contentcore.stream.domain.repository.StreamChannelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StreamChannelServiceImpl implements StreamChannelService {
    private final StreamChannelPlatformMapper streamChannelPlatformMapper;
    private final StreamChannelRepository streamChannelRepository;

    @EventListener
    public void handleNewContentAccountAddedEvent(NewContentAccountAddedEvent event) {
        try {
            StreamChannelPlatform platform = streamChannelPlatformMapper.getStreamChannelPlatform(event.getPlatform());
            StreamChannel newChannel = new StreamChannel(new StreamChannelContentAccountId(event.getContentAccountId()), platform);
            streamChannelRepository.save(newChannel);
        } catch (ErrorCreatingStreamChannelPlatform ex) {
            log.info(ex.getMessage());
        }
    }

    @EventListener
    public void handleContentAccountBecameActiveEvent(ContentAccountBecameActiveEvent event) {
        StreamChannelContentAccountId id = new StreamChannelContentAccountId(event.getContentAccountId());
        StreamChannel streamChannel;
        try {
            streamChannel = streamChannelRepository.getByContentAccountId(id);
        } catch (StreamChannelWithContentAccountIdNotFoundException ex) {
            return;
        }
        streamChannel.startObserving();
        streamChannelRepository.save(streamChannel);
    }

    @EventListener
    public void handleContentAccountBecameFrozenEvent(ContentAccountBecameFrozenEvent event) {
        StreamChannelContentAccountId id = new StreamChannelContentAccountId(event.getContentAccountId());
        StreamChannel streamChannel;
        try {
            streamChannel = streamChannelRepository.getByContentAccountId(id);
        } catch (StreamChannelWithContentAccountIdNotFoundException ex) {
            return;
        }
        streamChannel.freeze();
        streamChannelRepository.save(streamChannel);
    }
}
