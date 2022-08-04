package com.github.he305.contentcore.watchinglist.application.commands;

import com.github.he305.contentcore.watchinglist.application.dto.WatchingListEntryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class UpdateWatchingEntryCommand {
    private UUID memberId;
    private WatchingListEntryDto dto;
}
