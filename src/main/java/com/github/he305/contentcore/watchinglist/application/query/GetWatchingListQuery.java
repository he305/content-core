package com.github.he305.contentcore.watchinglist.application.query;

import com.github.he305.contentcore.watchinglist.application.dto.WatchingListEntryDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class GetWatchingListQuery {
    private List<WatchingListEntryDto> watchingList;
}
