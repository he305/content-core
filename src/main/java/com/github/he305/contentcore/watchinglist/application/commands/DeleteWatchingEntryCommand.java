package com.github.he305.contentcore.watchinglist.application.commands;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class DeleteWatchingEntryCommand {
    private UUID memberId;
    private String entryName;
}
