package com.github.he305.contentcore.watchinglist.application.query;

import com.github.he305.contentcore.watchinglist.application.dto.query.GetWatchingListEntryDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class GetWatchingListQuery {
    private List<GetWatchingListEntryDto> data;
}
