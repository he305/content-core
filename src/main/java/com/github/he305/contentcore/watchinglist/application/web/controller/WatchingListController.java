package com.github.he305.contentcore.watchinglist.application.web.controller;

import com.github.he305.contentcore.watchinglist.application.commands.AddWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.commands.CreateWatchingListCommand;
import com.github.he305.contentcore.watchinglist.application.commands.UpdateWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.dto.AddWatchingEntryDto;
import com.github.he305.contentcore.watchinglist.application.dto.CreateWatchingListDto;
import com.github.he305.contentcore.watchinglist.application.dto.MemberIdDto;
import com.github.he305.contentcore.watchinglist.application.dto.UpdateWatchingEntryDto;
import com.github.he305.contentcore.watchinglist.application.query.GetWatchingListQuery;
import com.github.he305.contentcore.watchinglist.application.service.AddWatchingEntryService;
import com.github.he305.contentcore.watchinglist.application.service.CreateWatchingListService;
import com.github.he305.contentcore.watchinglist.application.service.GetWatchingListService;
import com.github.he305.contentcore.watchinglist.application.service.UpdateWatchingEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.UUID;

@RestController
@RequestMapping("/watchingList")
@RequiredArgsConstructor
@Slf4j
public class WatchingListController {

    private final CreateWatchingListService createWatchingListService;
    private final GetWatchingListService getWatchingListService;
    private final AddWatchingEntryService addWatchingEntryService;
    private final UpdateWatchingEntryService updateWatchingEntryService;

    @PostMapping
    public ResponseEntity<Void> createWatchingList(@RequestBody CreateWatchingListDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        CreateWatchingListCommand command = new CreateWatchingListCommand(id, dto.getWatchingList());
        createWatchingListService.execute(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Void> addWatchingEntry(@RequestBody AddWatchingEntryDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        AddWatchingEntryCommand command = new AddWatchingEntryCommand(id, dto.getEntry());
        try {
            addWatchingEntryService.addWatchingEntry(command);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetWatchingListQuery> getWatchingList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        MemberIdDto dto = new MemberIdDto(id);
        return ResponseEntity.ok(getWatchingListService.getWatchingList(dto));
    }

    @PutMapping
    public ResponseEntity<Void> updateWatchingListEntry(@RequestBody UpdateWatchingEntryDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        UpdateWatchingEntryCommand command = new UpdateWatchingEntryCommand(id, dto.getDto());
        updateWatchingEntryService.updateWatchingEntry(command);
        return ResponseEntity.ok().build();
    }
}
