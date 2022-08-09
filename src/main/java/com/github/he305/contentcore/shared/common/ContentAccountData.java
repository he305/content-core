package com.github.he305.contentcore.shared.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class ContentAccountData {
    private final UUID contentAccountId;
    private final String stringData;
}
