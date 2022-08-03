package com.github.he305.contentcore.watchinglist.application.commands;

import com.github.he305.contentcore.watchinglist.application.dto.WatchingListEntryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWatchingListCommand {
    private UUID memberId;
    private List<WatchingListEntryDto> watchingList;
}
