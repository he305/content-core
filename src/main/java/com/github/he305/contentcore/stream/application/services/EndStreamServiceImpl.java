package com.github.he305.contentcore.stream.application.services;

import com.github.he305.contentcore.stream.application.commands.EndStreamCommand;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.repository.StreamChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EndStreamServiceImpl implements EndStreamService {
    private final StreamChannelRepository streamChannelRepository;

    @Override
    public void execute(EndStreamCommand command) {
        StreamChannel streamChannel = streamChannelRepository.getById(command.getStreamChannelId());
        streamChannel.endStream(command.getTime());
        streamChannelRepository.save(streamChannel);
    }
}
