package com.github.he305.contentcore.stream.application.services;

import com.github.he305.contentcore.stream.application.commands.PostStreamerDataCommand;
import com.github.he305.contentcore.stream.domain.model.StreamChannel;
import com.github.he305.contentcore.stream.domain.model.entities.StreamData;
import com.github.he305.contentcore.stream.domain.repository.StreamChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostStreamerDataServiceImpl implements PostStreamerDataService {
    private final StreamChannelRepository streamChannelRepository;

    @Override
    public void execute(PostStreamerDataCommand command) {
        StreamChannel streamChannel = streamChannelRepository.getById(command.getStreamChannelId());
        StreamData streamData = new StreamData(
                command.getName(),
                command.getTitle(),
                command.getViewerCount(),
                command.getTime()
        );

        streamChannel.addStreamData(streamData);
        streamChannelRepository.save(streamChannel);
    }
}
