package com.github.he305.contentcore.watchinglist.application.web.controller;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/watching-list")
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
    public void createWatchingList(@RequestBody CreateWatchingListDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        CreateWatchingListCommand command = new CreateWatchingListCommand(id, dto.getData());
        createWatchingListService.execute(command);
    }

    @PostMapping(value = "/add")
    public void addWatchingEntry(@RequestBody AddWatchingEntryDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        AddWatchingEntryCommand command = new AddWatchingEntryCommand(id, dto.getData());
        addWatchingEntryService.addWatchingEntry(command);
    }

    @GetMapping()
    public GetWatchingListQuery getWatchingList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        MemberIdDto dto = new MemberIdDto(id);
        return getWatchingListService.getWatchingList(dto);
    }

    @PostMapping(value = "/notification")
    public GetNotificationForContentAccountDto getNotificationsForContentAccount(@RequestBody NotificationForContentAccountDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        GetNotificationForContentAccountQuery query = new GetNotificationForContentAccountQuery(new MemberId(id), dto.getContentAccountName(), dto.getPlatform());
        return getNotificationForContentAccountService.execute(query);
    }

    @PutMapping
    public void updateWatchingListEntry(@RequestBody UpdateWatchingEntryDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        UpdateWatchingEntryCommand command = new UpdateWatchingEntryCommand(id, dto.getData());
        updateWatchingEntryService.updateWatchingEntry(command);
    }

    @DeleteMapping
    public void deleteWatchingListEntry(@RequestBody DeleteWatchingEntryDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        DeleteWatchingEntryCommand command = new DeleteWatchingEntryCommand(id, dto.getEntryName());
        deleteWatchingEntryService.execute(command);
    }

    @GetMapping(value = "/platforms")
    public PlatformsDto getPlatforms() {
        return getPlatformsService.execute();
    }
}
