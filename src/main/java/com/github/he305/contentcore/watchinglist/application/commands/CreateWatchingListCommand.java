package com.github.he305.contentcore.watchinglist.application.commands;

import com.github.he305.contentcore.watchinglist.application.dto.WatchingListEntryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class CreateWatchingListCommand {
    private UUID memberId;
    private List<WatchingListEntryDto> watchingList;
}
