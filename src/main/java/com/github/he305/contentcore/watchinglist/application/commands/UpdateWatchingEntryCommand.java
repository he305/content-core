package com.github.he305.contentcore.watchinglist.application.commands;

import com.github.he305.contentcore.watchinglist.application.dto.WatchingListEntryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWatchingEntryCommand {
    private UUID memberId;
    private WatchingListEntryDto dto;
}
