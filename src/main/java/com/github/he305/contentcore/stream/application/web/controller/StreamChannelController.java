package com.github.he305.contentcore.stream.application.web.controller;

import com.github.he305.contentcore.stream.application.commands.EndStreamCommand;
import com.github.he305.contentcore.stream.application.commands.PostStreamerDataCommand;
import com.github.he305.contentcore.stream.application.dto.StreamChannelList;
import com.github.he305.contentcore.stream.application.dto.StreamDataDto;
import com.github.he305.contentcore.stream.application.dto.StreamEndDto;
import com.github.he305.contentcore.stream.application.query.GetStreamChannelByStatusQuery;
import com.github.he305.contentcore.stream.application.services.EndStreamService;
import com.github.he305.contentcore.stream.application.services.GetStreamChannelByStatusService;
import com.github.he305.contentcore.stream.application.services.PostStreamerDataService;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stream")
@RequiredArgsConstructor
public class StreamChannelController {
    private final GetStreamChannelByStatusService getStreamChannelByStatusService;
    private final PostStreamerDataService postStreamerDataService;
    private final EndStreamService endStreamService;

    @GetMapping()
    public StreamChannelList getObservableStreamChannels() {
        GetStreamChannelByStatusQuery query = new GetStreamChannelByStatusQuery(StreamChannelStatus.OBSERVABLE);
        return getStreamChannelByStatusService.execute(query);
    }

    @PostMapping("/data")
    public void postStreamData(@RequestBody StreamDataDto dto) {
        PostStreamerDataCommand command = new PostStreamerDataCommand(
                dto.getStreamChannelId(),
                dto.getName(),
                dto.getTitle(),
                dto.getViewerCount(),
                dto.getTime()
        );
        postStreamerDataService.execute(command);
    }

    @PostMapping("/end")
    public void endStream(@RequestBody StreamEndDto dto) {
        EndStreamCommand command = new EndStreamCommand(dto.getStreamerChannelId(), dto.getEndTime());
        endStreamService.execute(command);
    }
}
