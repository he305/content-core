package com.github.he305.contentcore.watchinglist.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class MemberIdDto {
    private UUID memberId;
}
