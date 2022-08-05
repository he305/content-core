package com.github.he305.contentcore.stream.application.web.controller;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stream")
@RequiredArgsConstructor
@Slf4j
public class StreamChannelController {
    private final GetStreamChannelByStatusService getStreamChannelByStatusService;
    private final PostStreamerDataService postStreamerDataService;
    private final EndStreamService endStreamService;

    @GetMapping("/{status}")
    public ResponseEntity<StreamChannelList> getStreamChannelsByStatus(@PathVariable StreamChannelStatus status) {
        try {
            GetStreamChannelByStatusQuery query = new GetStreamChannelByStatusQuery(status);
            return ResponseEntity.ok(getStreamChannelByStatusService.execute(query));
        } catch (ContentCoreException ex) {
            log.error(ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/data")
    public ResponseEntity<Void> postStreamData(@RequestBody StreamDataDto dto) {
        PostStreamerDataCommand command = new PostStreamerDataCommand(
                dto.getStreamChannelId(),
                dto.getName(),
                dto.getTitle(),
                dto.getViewerCount(),
                dto.getTime()
        );
        try {
            postStreamerDataService.execute(command);
            return ResponseEntity.ok().build();
        } catch (ContentCoreException ex) {
            log.error(ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/end")
    public ResponseEntity<Void> endStream(@RequestBody StreamEndDto dto) {
        EndStreamCommand command = new EndStreamCommand(dto.getStreamerChannelId(), dto.getEndTime());
        try {
            endStreamService.execute(command);
            return ResponseEntity.ok().build();
        } catch (ContentCoreException ex) {
            log.error(ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }
}
