package com.github.he305.contentcore.watchinglist.application.dto.query;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class GetWatchingListEntryDto {
    private String name;
    private List<GetContentAccountDto> accounts;
}
