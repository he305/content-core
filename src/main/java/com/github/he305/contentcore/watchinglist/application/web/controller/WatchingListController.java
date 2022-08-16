package com.github.he305.contentcore.watchinglist.application.web.controller;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;
import com.github.he305.contentcore.watchinglist.application.commands.AddWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.commands.CreateWatchingListCommand;
import com.github.he305.contentcore.watchinglist.application.commands.DeleteWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.commands.UpdateWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.dto.*;
import com.github.he305.contentcore.watchinglist.application.dto.query.GetNotificationForContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.query.NotificationForContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.query.GetNotificationForContentAccountQuery;
import com.github.he305.contentcore.watchinglist.application.query.GetWatchingListQuery;
import com.github.he305.contentcore.watchinglist.application.service.*;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    private final GetNotificationForContentAccountService getNotificationForContentAccountService;
    private final DeleteWatchingEntryService deleteWatchingEntryService;
    private final GetPlatformsService getPlatformsService;

    @PostMapping
    public ResponseEntity<Void> createWatchingList(@RequestBody CreateWatchingListDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        CreateWatchingListCommand command = new CreateWatchingListCommand(id, dto.getData());
        try {
            createWatchingListService.execute(command);
            return ResponseEntity.ok().build();
        } catch (ContentCoreException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Void> addWatchingEntry(@RequestBody AddWatchingEntryDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        AddWatchingEntryCommand command = new AddWatchingEntryCommand(id, dto.getData());
        try {
            addWatchingEntryService.addWatchingEntry(command);
            return ResponseEntity.ok().build();
        } catch (ContentCoreException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping()
    public ResponseEntity<GetWatchingListQuery> getWatchingList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        MemberIdDto dto = new MemberIdDto(id);
        try {
            return ResponseEntity.ok(getWatchingListService.getWatchingList(dto));
        } catch (ContentCoreException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/notification")
    public ResponseEntity<GetNotificationForContentAccountDto> getNotificationsForContentAccount(@RequestBody NotificationForContentAccountDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        GetNotificationForContentAccountQuery query = new GetNotificationForContentAccountQuery(new MemberId(id), dto.getContentAccountName(), dto.getPlatform());
        try {
            return ResponseEntity.ok(getNotificationForContentAccountService.execute(query));
        } catch (ContentCoreException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateWatchingListEntry(@RequestBody UpdateWatchingEntryDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        UpdateWatchingEntryCommand command = new UpdateWatchingEntryCommand(id, dto.getData());
        try {
            updateWatchingEntryService.updateWatchingEntry(command);
            return ResponseEntity.ok().build();
        } catch (ContentCoreException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteWatchingListEntry(@RequestBody DeleteWatchingEntryDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        DeleteWatchingEntryCommand command = new DeleteWatchingEntryCommand(id, dto.getEntryName());
        try {
            deleteWatchingEntryService.execute(command);
            return ResponseEntity.ok().build();
        } catch (ContentCoreException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/platforms")
    public ResponseEntity<PlatformsDto> getPlatforms() {
        return ResponseEntity.ok(getPlatformsService.execute());
    }
}
