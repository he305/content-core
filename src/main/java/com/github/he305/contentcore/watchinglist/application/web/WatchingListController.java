package com.github.he305.contentcore.watchinglist.application.web;

import com.github.he305.contentcore.watchinglist.application.commands.AddWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.commands.CreateWatchingListCommand;
import com.github.he305.contentcore.watchinglist.application.commands.UpdateWatchingEntryCommand;
import com.github.he305.contentcore.watchinglist.application.dto.MemberIdDto;
import com.github.he305.contentcore.watchinglist.application.query.GetWatchingListQuery;
import com.github.he305.contentcore.watchinglist.application.service.AddWatchingEntryService;
import com.github.he305.contentcore.watchinglist.application.service.CreateWatchingListService;
import com.github.he305.contentcore.watchinglist.application.service.GetWatchingListService;
import com.github.he305.contentcore.watchinglist.application.service.UpdateWatchingEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/watchingList")
@RequiredArgsConstructor
public class WatchingListController {

    private final CreateWatchingListService createWatchingListService;
    private final GetWatchingListService getWatchingListService;
    private final AddWatchingEntryService addWatchingEntryService;
    private final UpdateWatchingEntryService updateWatchingEntryService;

    @PostMapping
    public ResponseEntity<Void> createWatchingList(@RequestBody CreateWatchingListCommand command) {
        createWatchingListService.execute(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Void> addWatchingEntry(@RequestBody AddWatchingEntryCommand command) {
        addWatchingEntryService.addWatchingEntry(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetWatchingListQuery> getWatchingList(@PathVariable UUID id) {
        MemberIdDto dto = new MemberIdDto(id);
        return ResponseEntity.ok(getWatchingListService.getWatchingList(dto));
    }

    @PutMapping
    public ResponseEntity<Void> updateWatchingListEntry(@RequestBody UpdateWatchingEntryCommand command) {
        updateWatchingEntryService.updateWatchingEntry(command);
        return ResponseEntity.ok().build();
    }
}
