package com.github.he305.contentcore.watchinglist.application.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WatchingListEntryDto {
    private String name;
    private List<ContentAccountDto> accounts;
}
