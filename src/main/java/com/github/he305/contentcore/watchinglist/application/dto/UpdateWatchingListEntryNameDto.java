package com.github.he305.contentcore.watchinglist.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class UpdateWatchingListEntryNameDto {
    private String oldName;
    private String newName;
}
